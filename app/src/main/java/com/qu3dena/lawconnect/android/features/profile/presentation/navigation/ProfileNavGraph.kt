package com.qu3dena.lawconnect.android.features.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.profile.presentation.ui.screen.ProfileView
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

/**
 * Profile feature navigation routes.
 */
sealed class ProfileScreen(val route: String) {
    object Main : ProfileScreen("profile_main")
}

/**
 * Profile feature navigation graph implementation.
 * 
 * This class implements the FeatureNavGraph contract and provides
 * all profile-related navigation routes.
 */
class ProfileNavGraph : FeatureNavGraph {
    
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            route = "profile_graph",
            startDestination = ProfileScreen.Main.route
        ) {
            composable(route = ProfileScreen.Main.route) {
                ProfileView(
                    onSignOut = { /* Handle sign out action */ }
                )
            }
        }
    }
}