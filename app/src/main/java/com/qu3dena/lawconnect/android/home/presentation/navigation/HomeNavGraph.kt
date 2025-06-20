package com.qu3dena.lawconnect.android.home.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.AuthViewModel
import com.qu3dena.lawconnect.android.home.presentation.ui.screen.HomeView
import com.qu3dena.lawconnect.android.navigation.Graph

sealed class HomeScreen(val route: String) {
    object Main : HomeScreen("home_main")
}

fun NavGraphBuilder.homeNavGraph(
    route: String,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    navigation(
        route = route,
        startDestination = HomeScreen.Main.route
    ) {
        composable(HomeScreen.Main.route) {

            HomeView(
                onSignOut = {
                    // 1) limpiamos sesión
                    authViewModel.signOut()

                    // 2) navegamos de vuelta al auth_graph
                    navController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Root.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}