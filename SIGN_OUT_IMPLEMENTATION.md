# Sign Out Implementation - Clean Architecture

This document explains how the sign out functionality is implemented following **Domain Driven Design** and **Clean Architecture** principles.

## 🏗️ Architecture Overview

The sign out functionality follows Clean Architecture layers:

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION                             │
├─────────────────────────────────────────────────────────────┤
│  ProfileView → ProfileViewModel → SignOutUseCase           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      DOMAIN                                 │
├─────────────────────────────────────────────────────────────┤
│  SignOutUseCase → AuthRepository (interface)               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       DATA                                  │
├─────────────────────────────────────────────────────────────┤
│  AuthRepositoryImpl → AuthPreferences → SharedPreferences  │
└─────────────────────────────────────────────────────────────┘
```

## 📁 File Structure

```
features/
├── auth/
│   ├── domain/
│   │   ├── repository/
│   │   │   └── AuthRepository.kt           # Domain contract
│   │   └── usecases/
│   │       └── SignOutUseCase.kt           # Business logic
│   └── data/
│       └── repository/
│           └── AuthRepositoryImpl.kt       # Data implementation
└── profile/
    └── presentation/
        ├── ui/
        │   ├── screen/
        │   │   └── ProfileView.kt          # UI layer
        │   └── viewmodels/
        │       ├── ProfileViewModel.kt     # Presentation logic
        │       └── ProfileViewModelTest.kt # Unit tests
        └── navigation/
            └── ProfileNavGraph.kt          # Navigation logic
```

## 🔧 Implementation Details

### **1. Domain Layer - Business Logic**

#### **SignOutUseCase**
```kotlin
class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke() {
        try {
            repository.signOut().first()
        } catch (e: Exception) {
            throw e
        }
    }
}
```

**Responsibilities:**
- ✅ Encapsulates sign out business logic
- ✅ Uses repository interface (dependency inversion)
- ✅ Handles exceptions properly
- ✅ Single responsibility principle

### **2. Data Layer - Implementation**

#### **AuthRepository Interface**
```kotlin
interface AuthRepository {
    fun signOut(): Flow<Unit>
    // ... other methods
}
```

#### **AuthRepositoryImpl**
```kotlin
class AuthRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences
) : AuthRepository {
    override fun signOut(): Flow<Unit> = flow {
        authPreferences.clearToken()
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}
```

**Responsibilities:**
- ✅ Implements domain contract
- ✅ Handles data operations
- ✅ Uses dependency injection
- ✅ Runs on background thread

### **3. Presentation Layer - UI Logic**

#### **ProfileViewModel**
```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onSignOut() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSigningOut = true)
                signOutUseCase.invoke()
                _uiState.value = _uiState.value.copy(
                    isSigningOut = false,
                    signOutSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSigningOut = false,
                    error = e.message
                )
            }
        }
    }
}
```

**Responsibilities:**
- ✅ Manages UI state
- ✅ Handles user interactions
- ✅ Uses use cases for business logic
- ✅ Provides reactive state updates

#### **ProfileView**
```kotlin
@Composable
fun ProfileView(
    onSignOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.signOutSuccess) {
        if (uiState.signOutSuccess) {
            onSignOut()
            viewModel.resetSignOutSuccess()
        }
    }

    // UI implementation with loading states and error handling
}
```

**Responsibilities:**
- ✅ Observes ViewModel state
- ✅ Handles UI interactions
- ✅ Shows loading and error states
- ✅ Delegates navigation to parent

### **4. Navigation Layer**

#### **ProfileNavGraph**
```kotlin
class ProfileNavGraph : FeatureNavGraph {
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(route = "profile_graph") {
            composable(route = ProfileScreen.Main.route) {
                ProfileView(
                    onSignOut = {
                        navController.navigate("auth_graph") {
                            popUpTo("root_graph") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
```

**Responsibilities:**
- ✅ Handles navigation within feature
- ✅ Clears back stack on sign out
- ✅ Navigates to auth screen

## 🎯 Clean Architecture Principles Applied

### **✅ Dependency Inversion**
- Domain layer defines `AuthRepository` interface
- Data layer implements the interface
- Presentation layer depends on domain abstractions

### **✅ Separation of Concerns**
- **Domain**: Business logic (SignOutUseCase)
- **Data**: Data operations (AuthRepositoryImpl)
- **Presentation**: UI logic (ProfileViewModel, ProfileView)
- **Navigation**: Routing logic (ProfileNavGraph)

### **✅ Single Responsibility**
- Each class has one clear purpose
- Use cases handle specific business operations
- ViewModels manage UI state
- Views handle UI rendering

### **✅ Testability**
- All dependencies are injected
- Business logic is isolated in use cases
- UI logic is separated from business logic
- Easy to mock dependencies

## 🧪 Testing Strategy

### **Unit Tests**
```kotlin
@Test
fun `onSignOut success should update state correctly`() = runTest {
    // Given
    whenever(signOutUseCase.invoke()).thenReturn(Unit)

    // When
    viewModel.onSignOut()
    testDispatcher.scheduler.advanceUntilIdle()

    // Then
    val state = viewModel.uiState.value
    assertFalse(state.isSigningOut)
    assertTrue(state.signOutSuccess)
    assertNull(state.error)
}
```

### **Integration Tests**
- Test complete sign out flow
- Test navigation after sign out
- Test data persistence clearing

### **UI Tests**
- Test sign out button interaction
- Test loading states
- Test error handling

## 🚀 Usage Examples

### **Basic Sign Out**
```kotlin
// In ProfileView
BrownActionButton(
    text = "Sign Out",
    onClick = { viewModel.onSignOut() }
)
```

### **Handle Sign Out Success**
```kotlin
// In ProfileView
LaunchedEffect(uiState.signOutSuccess) {
    if (uiState.signOutSuccess) {
        onSignOut() // Navigate to auth screen
        viewModel.resetSignOutSuccess()
    }
}
```

### **Handle Errors**
```kotlin
// In ProfileView
uiState.error?.let { error ->
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error
    )
}
```

## 🔍 Error Handling

### **Repository Level**
```kotlin
override fun signOut(): Flow<Unit> = flow {
    try {
        authPreferences.clearToken()
        emit(Unit)
    } catch (e: Exception) {
        throw e
    }
}.flowOn(Dispatchers.IO)
```

### **Use Case Level**
```kotlin
suspend fun invoke() {
    try {
        repository.signOut().first()
    } catch (e: Exception) {
        throw e
    }
}
```

### **ViewModel Level**
```kotlin
fun onSignOut() {
    viewModelScope.launch {
        try {
            // Update loading state
            signOutUseCase.invoke()
            // Update success state
        } catch (e: Exception) {
            // Update error state
        }
    }
}
```

## 📋 Best Practices

### **1. State Management**
- Use sealed classes for complex states
- Handle loading, success, and error states
- Provide clear state transitions

### **2. Error Handling**
- Handle errors at appropriate layers
- Provide meaningful error messages
- Allow error recovery

### **3. Testing**
- Test each layer independently
- Mock dependencies properly
- Test error scenarios

### **4. Navigation**
- Clear back stack on sign out
- Navigate to appropriate screen
- Handle navigation state properly

## 🎉 Benefits

### **Maintainability**
- ✅ Clear separation of concerns
- ✅ Easy to modify business logic
- ✅ Easy to change UI implementation

### **Testability**
- ✅ Each layer can be tested independently
- ✅ Dependencies are easily mocked
- ✅ Business logic is isolated

### **Scalability**
- ✅ Easy to add new features
- ✅ Easy to modify existing features
- ✅ Consistent architecture patterns

### **Reliability**
- ✅ Proper error handling
- ✅ State management
- ✅ Navigation handling

---

This implementation demonstrates how to properly structure sign out functionality following Clean Architecture principles, ensuring maintainable, testable, and scalable code. 