package com.qu3dena.lawconnect.android.clients.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.clients.presentation.ui.screen.ClientsView

sealed class ClientScreen(val route: String) {
    object Main : ClientScreen("client_main")
}

fun NavGraphBuilder.clientsNavGraph(
    route: String,
    navController: NavHostController,
) {
    navigation(
        route = route,
        startDestination = ClientScreen.Main.route
    ) {
        composable(ClientScreen.Main.route) {
            ClientsView()
        }
    }
}