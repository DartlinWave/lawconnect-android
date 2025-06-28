package com.qu3dena.lawconnect.android.features.clients.presentation.navigation

import androidx.navigation.navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.clients.presentation.ui.screens.ClientsView

/**
 * Client feature navigation routes.
 */
sealed class ClientScreen(val route: String) {
    object Main : ClientScreen("client_main")
}

/**
 * Client feature navigation graph implementation.
 * 
 * This class implements the FeatureNavGraph contract and provides
 * all client-related navigation routes.
 */
class ClientNavGraph : FeatureNavGraph {
    
    override fun build(
        builder: NavGraphBuilder, 
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        builder.navigation(
            route = "client_graph",
            startDestination = ClientScreen.Main.route
        ) {
            composable(route = ClientScreen.Main.route) {
                ClientsView()
            }
        }
    }
}