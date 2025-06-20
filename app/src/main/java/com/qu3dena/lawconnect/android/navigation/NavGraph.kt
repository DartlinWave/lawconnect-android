package com.qu3dena.lawconnect.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.qu3dena.lawconnect.android.auth.presentation.navigation.authNavGraph
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.AuthViewModel
import com.qu3dena.lawconnect.android.home.presentation.navigation.homeNavGraph

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel
) {
    NavHost(
        route = Graph.Root.route,
        navController = navController,
        startDestination = if (isLoggedIn) Graph.Home.route else Graph.Auth.route
    ) {
        authNavGraph(
            route = Graph.Auth.route,
            navController = navController,
            onLoginSuccess = {

                navController.navigate(Graph.Home.route) {
                    popUpTo(Graph.Auth.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        homeNavGraph(
            route = Graph.Home.route,
            navController = navController,
            authViewModel = authViewModel
        )
    }
}