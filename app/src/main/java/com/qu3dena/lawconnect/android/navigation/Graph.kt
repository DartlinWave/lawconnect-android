package com.qu3dena.lawconnect.android.navigation

/**
 * Represents a navigation graph.
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
     * Client section navigation graph.
     */
    object Client : Graph("client_graph")
}