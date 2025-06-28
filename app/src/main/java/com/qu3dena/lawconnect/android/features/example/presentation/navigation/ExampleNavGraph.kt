package com.qu3dena.lawconnect.android.features.example.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

/**
 * Example feature navigation routes.
 */
sealed class ExampleScreen(val route: String) {
    object Main : ExampleScreen("example_main")
    object Detail : ExampleScreen("example_detail")
}

/**
 * Example feature navigation graph implementation.
 * 
 * This class implements the FeatureNavGraph contract and provides
 * all example-related navigation routes.
 * 
 * This demonstrates how to create a new feature following Clean Architecture:
 * 1. Implement FeatureNavGraph interface
 * 2. Create a DI module with @IntoSet annotation
 * 3. No registration or reflection needed
 */
class ExampleNavGraph : FeatureNavGraph {
    
    override fun build(
        builder: NavGraphBuilder, 
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        builder.navigation(
            route = "example_graph",
            startDestination = ExampleScreen.Main.route
        ) {
            composable(route = ExampleScreen.Main.route) {
                ExampleMainView(
                    onNavigateToDetail = { navController.navigate(ExampleScreen.Detail.route) }
                )
            }
            
            composable(route = ExampleScreen.Detail.route) {
                ExampleDetailView(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun ExampleMainView(onNavigateToDetail: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Example Feature - Main Screen")
    }
}

@Composable
private fun ExampleDetailView(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Example Feature - Detail Screen")
    }
} 