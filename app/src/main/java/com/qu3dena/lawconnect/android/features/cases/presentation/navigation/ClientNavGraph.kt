package com.qu3dena.lawconnect.android.features.cases.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CaseClientsView
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.shared.navigation.Graph

class ClientNavGraph : FeatureNavGraph {

    override fun build(
        builder: NavGraphBuilder,
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        builder.navigation(
            route = Graph.Clients.route,
            startDestination = CaseScreen.Clients.route
        ) {
            composable(route = CaseScreen.Clients.route) {
                CaseClientsView()
            }
        }
    }
}