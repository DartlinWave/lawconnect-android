package com.qu3dena.lawconnect.android.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.AuthViewModel
import com.qu3dena.lawconnect.android.navigation.Graph
import com.qu3dena.lawconnect.android.profile.presentation.ui.screen.ProfileView

sealed class Screen(val route: String) {
    object Main : Screen("home_main")
}

fun NavGraphBuilder.profileNavGraph(
    route: String,
    navController: NavHostController,
    onSignOut: () -> Unit,
) {
    navigation(
        route = route,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {

            ProfileView (
                onSignOut = onSignOut
            )
        }
    }
}