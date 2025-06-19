package com.qu3dena.lawconnect.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.qu3dena.lawconnect.android.auth.presentation.navigation.authNavGraph

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        route = Graph.Root.route,
        navController = navController,
        startDestination = Graph.Auth.route
    ) {
        authNavGraph(
            route = Graph.Auth.route,
            navController = navController
        )
    }
}