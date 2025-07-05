# Clean Architecture Navigation System

This project implements a **Clean Architecture + Domain Driven Design** navigation system for Android Jetpack Compose that maintains proper separation of concerns and dependency inversion.

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                        PRESENTATION                        │
├─────────────────────────────────────────────────────────────┤
│  MainActivity ──> MainScreen ──> SetupNavGraph             │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                         CORE                                │
├─────────────────────────────────────────────────────────────┤
│  NavGraphModule ──> FeatureNavGraph (interface)            │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       FEATURES                             │
├─────────────────────────────────────────────────────────────┤
│  AuthNavGraphModule ──> AuthNavGraph (implementation)      │
│  ClientNavGraphModule ──> ClientNavGraph (implementation)  │
│  ProfileNavGraphModule ──> ProfileNavGraph (implementation)│
└─────────────────────────────────────────────────────────────┘
```

## 🎯 Clean Architecture Principles

### ✅ **Dependency Inversion**
- Core defines `FeatureNavGraph` interface
- Features provide implementations
- Core doesn't know about specific features

### ✅ **Separation of Concerns**
- Each feature manages its own navigation
- Core only orchestrates navigation graphs
- No cross-feature dependencies

### ✅ **Open/Closed Principle**
- Add new features without modifying core
- Features are "plugged in" via dependency injection

## 📁 File Structure

```
core/
├── di/
│   └── NavGraphModule.kt          # Core DI module
├── navigation/
│   ├── Graph.kt                   # Core navigation routes
│   ├── NavGraph.kt                # Main navigation setup
│   └── HomeNavGraph.kt            # Core home navigation
└── MainScreen.kt                  # Main screen composable

shared/
└── contracts/
    └── FeatureNavGraph.kt         # Navigation contract

features/
├── auth/
│   ├── di/
│   │   └── AuthNavGraphModule.kt  # Auth DI module
│   └── presentation/navigation/
│       └── AuthNavGraph.kt        # Auth navigation implementation
├── clients/
│   ├── di/
│   │   └── ClientNavGraphModule.kt
│   └── presentation/navigation/
│       └── ClientNavGraph.kt
└── profile/
    ├── di/
    │   └── ProfileNavGraphModule.kt
    └── presentation/navigation/
        └── ProfileNavGraph.kt
```

## 🔧 How It Works

### 1. **Core Defines the Contract**

```kotlin
// shared/contracts/FeatureNavGraph.kt
interface FeatureNavGraph {
    fun build(builder: NavGraphBuilder, navController: NavHostController)
}
```

### 2. **Core Provides Collection Point**

```kotlin
// core/di/NavGraphModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NavGraphModule {
    @Provides
    @Singleton
    fun provideFeatureNavGraphs(
        navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>
    ): List<@JvmSuppressWildcards FeatureNavGraph> {
        return navGraphs.toList()
    }
}
```

### 3. **Features Provide Implementations**

```kotlin
// features/auth/di/AuthNavGraphModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AuthNavGraphModule {
    @Provides
    @IntoSet
    fun provideAuthNavGraph(): FeatureNavGraph {
        return AuthNavGraph()
    }
}
```

### 4. **Features Implement Navigation**

```kotlin
// features/auth/presentation/navigation/AuthNavGraph.kt
class AuthNavGraph : FeatureNavGraph {
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            route = "auth_graph",
            startDestination = AuthScreen.SignIn.route
        ) {
            composable(route = AuthScreen.SignIn.route) {
                SignInView()
            }
        }
    }
}
```

### 5. **Main Screen Uses Injected Graphs**

```kotlin
// core/MainScreen.kt
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    featureNavGraphs: List<FeatureNavGraph> // Injected by Hilt
) {
    // ... setup navigation
    SetupNavGraph(
        navController = navController,
        featureNavGraphs = featureNavGraphs
    )
}
```

## 🚀 Adding a New Feature

### Step 1: Create Navigation Graph

```kotlin
// features/newfeature/presentation/navigation/NewFeatureNavGraph.kt
class NewFeatureNavGraph : FeatureNavGraph {
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            route = "newfeature_graph",
            startDestination = NewFeatureScreen.Main.route
        ) {
            composable(route = NewFeatureScreen.Main.route) {
                NewFeatureView()
            }
        }
    }
}
```

### Step 2: Create DI Module

```kotlin
// features/newfeature/di/NewFeatureNavGraphModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NewFeatureNavGraphModule {
    @Provides
    @IntoSet
    fun provideNewFeatureNavGraph(): FeatureNavGraph {
        return NewFeatureNavGraph()
    }
}
```

### Step 3: That's It!

The feature will be automatically included in navigation. No need to:
- ❌ Modify core code
- ❌ Register anything
- ❌ Use reflection
- ❌ Import feature code in core

## 🎯 Benefits

### **Clean Architecture**
- ✅ Core doesn't know about features
- ✅ Features depend on core abstractions
- ✅ Dependency inversion maintained

### **Scalability**
- ✅ Easy to add new features
- ✅ No impact on existing code
- ✅ Compile-time safety

### **Maintainability**
- ✅ Clear separation of concerns
- ✅ Easy to test each component
- ✅ No complex registration logic

### **Performance**
- ✅ No reflection overhead
- ✅ Compile-time dependency resolution
- ✅ Efficient dependency injection

## 🔍 Comparison with Other Approaches

| Approach | Clean Arch? | Easy to Add? | Performance | Complexity |
|----------|-------------|--------------|-------------|------------|
| **Manual List** | ❌ | ❌ | ✅ | ❌ |
| **Service Locator** | ❌ | ✅ | ✅ | ❌ |
| **Reflection** | ❌ | ✅ | ❌ | ❌ |
| **DI + @IntoSet** | ✅ | ✅ | ✅ | ✅ |

## 🧪 Testing

### Testing Feature Navigation

```kotlin
@Test
fun `test auth navigation graph`() {
    val navGraph = AuthNavGraph()
    val navController = TestNavHostController(context)
    
    // Test navigation setup
    navGraph.build(mockNavGraphBuilder, navController)
    
    // Verify routes are properly configured
    assertThat(navController.graph.hasDeepLink("auth_graph")).isTrue()
}
```

### Testing Core Navigation

```kotlin
@Test
fun `test core navigation collects all features`() {
    val featureGraphs = listOf(
        AuthNavGraph(),
        ClientNavGraph(),
        ProfileNavGraph()
    )
    
    val navController = TestNavHostController(context)
    SetupNavGraph(navController, featureGraphs)
    
    // Verify all feature graphs are included
    assertThat(navController.graph.routes).containsAll(
        "auth_graph", "client_graph", "profile_graph"
    )
}
```

## 📋 Best Practices

1. **Keep Navigation Graphs Simple**
   - Focus on routing logic
   - Delegate business logic to ViewModels

2. **Use Sealed Classes for Routes**
   ```kotlin
   sealed class AuthScreen(val route: String) {
       object SignIn : AuthScreen("sign_in")
       object SignUp : AuthScreen("sign_up")
   }
   ```

3. **Follow Naming Conventions**
   - Feature routes: `{feature}_graph`
   - Screen routes: descriptive names

4. **Test Each Component**
   - Test individual navigation graphs
   - Test core navigation integration

5. **Document Route Dependencies**
   - Clear route definitions
   - Navigation flow documentation

## 🎉 Conclusion

This navigation system provides:
- **True Clean Architecture** with proper dependency inversion
- **Easy feature addition** without core modifications
- **Excellent performance** with compile-time resolution
- **Simple maintenance** with clear separation of concerns

The key insight is using **Dependency Injection with `@IntoSet`** to automatically collect feature implementations, eliminating the need for manual registration, reflection, or core modifications. 