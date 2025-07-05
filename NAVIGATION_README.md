# Navigation Guide - LawConnect Android App

This document explains how navigation works in the LawConnect Android app and how to move between different screens and features.

## 🏗️ Navigation Architecture

The app uses **Clean Architecture + Domain Driven Design** with Jetpack Compose Navigation. Each feature manages its own navigation independently, and the core orchestrates all feature navigation graphs.

```
┌─────────────────────────────────────────────────────────────┐
│                    NAVIGATION FLOW                          │
├─────────────────────────────────────────────────────────────┤
│  MainActivity → MainScreen → SetupNavGraph → FeatureGraphs │
└─────────────────────────────────────────────────────────────┘
```

## 📱 App Structure

### **Authentication Flow**
- **Sign In** → **Sign Up** (2-step process)
- **Sign Up Step 1** → **Sign Up Step 2** → **Sign In**

### **Main App Flow** (After Login)
- **Home** (Dashboard)
- **Clients** (Client Management)
- **Cases** (Case Management) 
- **Profile** (User Profile & Settings)

## 🗂️ Directory Structure

```
app/src/main/java/com/qu3dena/lawconnect/android/
├── core/
│   ├── di/
│   │   └── NavGraphModule.kt          # Core navigation DI
│   ├── navigation/
│   │   ├── Graph.kt                   # Core route definitions
│   │   ├── NavGraph.kt                # Main navigation setup
│   │   └── HomeNavGraph.kt            # Home screen navigation
│   └── MainScreen.kt                  # Main app screen
├── shared/
│   └── contracts/
│       └── FeatureNavGraph.kt         # Navigation contract
└── features/
    ├── auth/                          # Authentication feature
    │   ├── di/
    │   │   └── AuthNavGraphModule.kt
    │   └── presentation/navigation/
    │       └── AuthNavGraph.kt
    ├── clients/                       # Clients feature
    │   ├── di/
    │   │   └── ClientNavGraphModule.kt
    │   └── presentation/navigation/
    │       └── ClientNavGraph.kt
    └── profile/                       # Profile feature
        ├── di/
        │   └── ProfileNavGraphModule.kt
        └── presentation/navigation/
            └── ProfileNavGraph.kt
```

## 🚀 How to Navigate Between Screens

### **From Code (Programmatic Navigation)**

#### 1. **Navigate to a Screen**
```kotlin
// Navigate to Sign In
navController.navigate("sign_in")

// Navigate to Clients
navController.navigate("client_graph")

// Navigate to Profile
navController.navigate("profile_graph")
```

#### 2. **Navigate with Arguments**
```kotlin
// Navigate with data
navController.navigate("client_detail/client123")

// Navigate with multiple arguments
navController.navigate("case_detail?caseId=123&status=active")
```

#### 3. **Navigate and Clear Back Stack**
```kotlin
// Navigate and clear all previous screens
navController.navigate("home_graph") {
    popUpTo("root_graph") { inclusive = true }
    launchSingleTop = true
}
```

#### 4. **Go Back**
```kotlin
// Go back one screen
navController.popBackStack()

// Go back to specific screen
navController.popBackStack("auth_graph", false)
```

### **From UI (User Interaction)**

#### **Bottom Navigation Bar**
The app has a bottom navigation bar with these options:
- 🏠 **Home** - Main dashboard
- 👥 **Clients** - Client management
- 📋 **Cases** - Case management  
- 👤 **Profile** - User profile

#### **Top Bar Navigation**
- **Back Button** - Returns to previous screen
- **Username Display** - Shows logged-in user

## 📋 Available Routes

### **Authentication Routes**
```kotlin
sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("sign_in")
    object SignUp : AuthScreen("sign_up")
}

sealed class SignUpStep(val route: String) {
    object Step1 : SignUpStep("sign_up_step1")
    object Step2 : SignUpStep("sign_up_step2")
}
```

### **Feature Routes**
```kotlin
// Client routes
sealed class ClientScreen(val route: String) {
    object Main : ClientScreen("client_main")
}

// Profile routes  
sealed class ProfileScreen(val route: String) {
    object Main : ProfileScreen("profile_main")
}

// Core routes
sealed class Graph(val route: String) {
    object Root : Graph("root_graph")
    object Auth : Graph("auth_graph")
    object Home : Graph("home_graph")
    object Clients : Graph("client_graph")
    object Profile : Graph("profile_graph")
}
```

## 🔧 How to Add New Navigation

### **Step 1: Create Route Definitions**
```kotlin
// In your feature's navigation file
sealed class YourFeatureScreen(val route: String) {
    object Main : YourFeatureScreen("yourfeature_main")
    object Detail : YourFeatureScreen("yourfeature_detail")
    object Settings : YourFeatureScreen("yourfeature_settings")
}
```

### **Step 2: Create Navigation Graph**
```kotlin
class YourFeatureNavGraph : FeatureNavGraph {
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            route = "yourfeature_graph",
            startDestination = YourFeatureScreen.Main.route
        ) {
            composable(route = YourFeatureScreen.Main.route) {
                YourFeatureMainView(
                    onNavigateToDetail = { 
                        navController.navigate(YourFeatureScreen.Detail.route) 
                    }
                )
            }
            
            composable(route = YourFeatureScreen.Detail.route) {
                YourFeatureDetailView(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
```

### **Step 3: Create DI Module**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object YourFeatureNavGraphModule {
    @Provides
    @IntoSet
    fun provideYourFeatureNavGraph(): FeatureNavGraph {
        return YourFeatureNavGraph()
    }
}
```

### **Step 4: Add to Bottom Navigation (Optional)**
```kotlin
// In MainScreen.kt, add to BottomBarScreen sealed class
object YourFeature : BottomBarScreen(
    route = Graph.YourFeature.route,
    title = "Your Feature",
    icon = Icons.Default.YourIcon
)
```

## 🎯 Navigation Patterns

### **1. Feature-to-Feature Navigation**
```kotlin
// From Clients to a specific client's cases
navController.navigate("cases_graph?clientId=${clientId}")

// From Cases to client details
navController.navigate("client_detail/$clientId")
```

### **2. Deep Linking**
```kotlin
// Handle deep links to specific screens
navController.navigate("client_detail/123") {
    launchSingleTop = true
}
```

### **3. Conditional Navigation**
```kotlin
// Navigate based on user state
if (isLoggedIn) {
    navController.navigate("home_graph")
} else {
    navController.navigate("auth_graph")
}
```

### **4. Navigation with Data**
```kotlin
// Pass data through navigation
val clientData = "client123"
navController.navigate("client_detail/$clientData")

// Or use arguments
navController.navigate("case_detail?caseId=123&status=active")
```

## 🔍 Debugging Navigation

### **Enable Navigation Logging**
```kotlin
// Add to your NavHost
NavHost(
    navController = navController,
    startDestination = "home_graph"
) {
    // Your navigation graphs
}
.onDestinationChanged { _, destination, _ ->
    Log.d("Navigation", "Navigated to: ${destination.route}")
}
```

### **Check Current Route**
```kotlin
val currentRoute = navController.currentBackStackEntry?.destination?.route
Log.d("Navigation", "Current route: $currentRoute")
```

### **List All Available Routes**
```kotlin
navController.graph.nodes.forEach { node ->
    Log.d("Navigation", "Available route: ${node.route}")
}
```

## 🧪 Testing Navigation

### **Test Navigation Setup**
```kotlin
@Test
fun `test navigation setup`() {
    val navController = TestNavHostController(context)
    val featureGraphs = listOf(AuthNavGraph(), ClientNavGraph())
    
    SetupNavGraph(navController, featureGraphs)
    
    assertThat(navController.graph.routes).contains("auth_graph")
    assertThat(navController.graph.routes).contains("client_graph")
}
```

### **Test Navigation Actions**
```kotlin
@Test
fun `test navigate to client screen`() {
    val navController = TestNavHostController(context)
    
    navController.navigate("client_graph")
    
    assertThat(navController.currentDestination?.route).isEqualTo("client_graph")
}
```

## 📱 Common Navigation Scenarios

### **Login Flow**
1. App starts → Check if user is logged in
2. If not logged in → Navigate to `auth_graph`
3. User signs in → Navigate to `home_graph` (clear back stack)
4. User signs up → Navigate through `sign_up_step1` → `sign_up_step2` → `sign_in`

### **Main App Flow**
1. User is logged in → Start at `home_graph`
2. User taps Clients → Navigate to `client_graph`
3. User taps Cases → Navigate to `cases_graph`
4. User taps Profile → Navigate to `profile_graph`

### **Sign Out Flow**
1. User taps Sign Out in Profile
2. Clear all navigation back stack
3. Navigate to `auth_graph`

## 🚨 Common Issues & Solutions

### **Issue: Navigation not working**
**Solution:** Check if the route exists in your navigation graph and the feature is properly registered in DI.

### **Issue: Back button not working**
**Solution:** Make sure you're not clearing the back stack unintentionally. Use `popBackStack()` for going back.

### **Issue: Feature not appearing**
**Solution:** Verify the feature's DI module is properly annotated with `@IntoSet` and the feature implements `FeatureNavGraph`.

### **Issue: Navigation arguments not received**
**Solution:** Check the argument types and make sure they match between the sending and receiving screens.

## 📚 Additional Resources

- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

**Remember:** Always follow the Clean Architecture principles - features should not directly import or depend on other features. Use the core navigation system to coordinate between features. 