package com.qu3dena.lawconnect.android.core.navigation

import androidx.navigation.navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.qu3dena.lawconnect.android.core.ui.screens.HomeView

sealed class HomeScreen(val route: String) {
    object Main : HomeScreen("home_main")
}

fun NavGraphBuilder.homeNavGraph(
    route: String,
    navController: NavHostController,
) {
    navigation(
        route = route,
        startDestination = HomeScreen.Main.route
    ) {
        composable(HomeScreen.Main.route) {
            HomeView()
        }
    }
}