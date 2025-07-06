package com.qu3dena.lawconnect.android.core.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager
import com.qu3dena.lawconnect.android.shared.navigation.Graph

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
    
    // Keeps track of the currently selected bottom bar destination
    private var currentBottomBarRoute: String? = null

    // Store the stack of visited bottom bar destinations to handle back navigation correctly
    private val bottomBarRouteStack = mutableListOf<String>()

    init {
        // Setup a destination change listener to update currentBottomBarRoute
        // when navigation occurs (including back navigation)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val route = destination.route ?: ""
            val baseRoute = route.split("?", "/").firstOrNull() ?: ""

            if (isBottomBarRoute(baseRoute)) {
                // Direct navigation to a bottom bar destination
                updateCurrentBottomBarDestination(baseRoute)
            } else if (isBottomBarRoute(route)) {
                // Direct navigation to a bottom bar destination (full route match)
                updateCurrentBottomBarDestination(route)
            } else {
                // For non-bottom bar destinations, we need to infer which section we're in

                // Check if this is the start destination of the Home graph
                // This is crucial for back navigation that returns to Home
                destination.hierarchy.forEach { hierarchyDestination ->
                    val hierarchyRoute = hierarchyDestination.route ?: ""

                    when (hierarchyRoute) {
                        Graph.Home.route -> {
                            // We're within the Home graph, so update to Home
                            updateCurrentBottomBarDestination(Graph.Home.route)
                            return@forEach
                        }
                        Graph.Cases.route -> {
                            updateCurrentBottomBarDestination(Graph.Cases.route)
                            return@forEach
                        }
                        Graph.Clients.route -> {
                            updateCurrentBottomBarDestination(Graph.Clients.route)
                            return@forEach
                        }
                        Graph.Profile.route -> {
                            updateCurrentBottomBarDestination(Graph.Profile.route)
                            return@forEach
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the current bottom bar destination and maintains the navigation stack
     */
    private fun updateCurrentBottomBarDestination(route: String) {
        if (route != currentBottomBarRoute) {
            // Only add to stack if it's a new destination
            currentBottomBarRoute = route

            // Update the navigation stack to track history
            if (bottomBarRouteStack.lastOrNull() != route) {
                bottomBarRouteStack.add(route)
                // Keep stack at a reasonable size
                if (bottomBarRouteStack.size > 10) {
                    bottomBarRouteStack.removeAt(0)
                }
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
        // Update the currently selected route
        updateCurrentBottomBarDestination(route)

        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    /**
     * Determines if the provided route corresponds to the currently selected destination.
     * This method centralizes the active route determination logic following Clean Architecture principles.
     *
     * @param route The route to check
     * @param currentDestination The current navigation destination
     * @return true if the route corresponds to the current destination, false otherwise
     */
    fun isRouteSelected(route: String, currentDestination: NavDestination?): Boolean {
        // For bottom bar routes, primarily use our tracked route for selection state
        if (isBottomBarRoute(route)) {
            return route == currentBottomBarRoute
        }

        // If no destination is set yet, use the current bottom bar route for comparison
        if (currentDestination == null) {
            return false
        }

        // Get full route string (including any arguments)
        val fullCurrentRoute = currentDestination.route ?: ""

        // Extract base route (without parameters)
        val currentBaseRoute = fullCurrentRoute.split("?", "/").firstOrNull() ?: ""

        // Check for direct match
        if (route == currentBaseRoute || route == fullCurrentRoute) {
            return true
        }

        // Finally check hierarchy match for non-bottom bar routes
        return currentDestination.hierarchy.any { it.route == route }
    }

    /**
     * Determines if a route is a bottom bar destination
     */
    private fun isBottomBarRoute(route: String): Boolean {
        // List all bottom bar routes for comparison
        val bottomBarRoutes = listOf(
            Graph.Home.route,
            Graph.Cases.route,
            Graph.Profile.route,
            Graph.Clients.route
        )
        return route in bottomBarRoutes
    }

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
}
