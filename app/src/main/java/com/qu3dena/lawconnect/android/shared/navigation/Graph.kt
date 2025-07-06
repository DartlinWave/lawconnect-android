package com.qu3dena.lawconnect.android.shared.navigation

/**
 * Represents a navigation graph.
 *
 * By placing this class in the shared layer, both core and feature modules
 * can access these routes directly, simplifying navigation while maintaining
 * architectural boundaries.
 *
 * @property route The route identifier for the graph.
 */
sealed class Graph(val route: String) {

    /**
     * The root navigation graph, which serves as the main entry point for the app.
     */
    object Root : Graph("root_graph")

    /**
     * Authentication-related navigation graph.
     */
    object Auth : Graph("auth_graph")

    /**
     * Navigation graph for cases.
     */
    object Cases : Graph("cases_graph")

    /**
     * Home section navigation graph.
     */
    object Home : Graph("home_graph")

    /**
     * Profile section navigation graph.
     */
    object Profile : Graph("profile_graph")

    /**
     * Clients section navigation graph.
     */
    object Clients : Graph("clients_graph")
}
