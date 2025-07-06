package com.qu3dena.lawconnect.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.PaddingValues
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.shared.navigation.Graph

/**
 * Main navigation graph setup.
 * 
 * This composable sets up the root navigation graph and includes all feature
 * navigation graphs that are provided through dependency injection.
 * 
 * Clean Architecture principles:
 * - Uses dependency injection to get feature navigation graphs
 * - Core doesn't know about specific features
 * - Features are plugged in through DI
 * - Core can provide callbacks to features via additionalParams
 */
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    isLoggedIn: Boolean,
    featureNavGraphs: List<FeatureNavGraph>,
    additionalParams: Map<String, Any> = emptyMap()
) {
    NavHost(
        route = Graph.Root.route,
        navController = navController,
        startDestination = if (isLoggedIn) Graph.Home.route else Graph.Auth.route
    ) {

        featureNavGraphs.forEach { featureNavGraph ->
            featureNavGraph.build(this, navController, additionalParams)
        }
    }
}
