package com.qu3dena.lawconnect.android.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager

/**
 * Navigation Coordinator that handles all navigation logic and parameter preparation.
 * 
 * This class follows Clean Architecture principles by:
 * - Centralizing navigation logic
 * - Keeping MainScreen clean and focused
 * - Making navigation logic testable and reusable
 * - Providing a single place to manage all navigation parameters
 * - Using contracts instead of concrete implementations
 */
class NavigationCoordinator(
    private val navController: NavHostController,
    private val authSessionManager: AuthSessionManager
) {
    
    /**
     * Prepares additional parameters for feature navigation graphs.
     * This method can be extended to include more navigation callbacks as the app grows.
     */
    fun prepareAdditionalParams(): Map<String, Any> {
        return mapOf(
            "onSignOut" to createSignOutCallback()
        )
    }
    
    /**
     * Creates the sign out callback with navigation logic.
     * This method encapsulates the sign out navigation behavior.
     */
    private fun createSignOutCallback(): () -> Unit {
        return {
            authSessionManager.signOut()
            navController.navigate(Graph.Auth.route) {
                popUpTo(Graph.Root.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    
    /**
     * Navigate to a specific route with optional pop behavior.
     * This method provides a centralized way to handle navigation.
     */
    fun navigateTo(route: String, popUpTo: String? = null, inclusive: Boolean = false) {
        navController.navigate(route) {
            popUpTo?.let { popRoute ->
                popUpTo(popRoute) { this.inclusive = inclusive }
            }
            launchSingleTop = true
        }
    }
    
    /**
     * Navigate to bottom bar destinations.
     * This method handles bottom bar navigation consistently.
     */
    fun navigateToBottomBarDestination(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }
} 