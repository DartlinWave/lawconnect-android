package com.qu3dena.lawconnect.android.features.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.home.presentation.ui.screens.HomeView
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

sealed class HomeScreen(val route: String) {
    object Main : HomeScreen("home_main")
}

class HomeNavGraph : FeatureNavGraph {

    override fun build(
        builder: NavGraphBuilder,
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        builder.navigation(
            route = "home_graph",
            startDestination = HomeScreen.Main.route
        ) {
            composable(route = HomeScreen.Main.route) {
                HomeView()
            }
        }
    }
}