package com.qu3dena.lawconnect.android.features.profile.presentation.navigation

import androidx.navigation.navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.profile.presentation.ui.screen.ProfileView

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
 * 
 * Clean Architecture principles:
 * - Handles navigation within the profile feature
 * - Receives sign out callback from core via additionalParams
 * - Maintains separation of concerns
 */
class ProfileNavGraph : FeatureNavGraph {
    
    override fun build(
        builder: NavGraphBuilder, 
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        @Suppress("UNCHECKED_CAST")
        val onSignOut = additionalParams["onSignOut"] as? (() -> Unit) ?: {}
        
        builder.navigation(
            route = "profile_graph",
            startDestination = ProfileScreen.Main.route
        ) {
            composable(route = ProfileScreen.Main.route) {
                ProfileView(onSignOut = onSignOut)
            }
        }
    }
}