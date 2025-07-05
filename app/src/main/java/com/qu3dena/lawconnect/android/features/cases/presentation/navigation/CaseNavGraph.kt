package com.qu3dena.lawconnect.android.features.cases.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CasesView
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

sealed class CaseScreen(val route: String) {
    object Main : CaseScreen("case_main")
}

class CaseNavGraph : FeatureNavGraph {

    override fun build(
        builder: NavGraphBuilder,
        navController: NavHostController,
        additionalParams: Map<String, Any>
    ) {
        builder.navigation(
            route = "cases_graph",
            startDestination = CaseScreen.Main.route
        ) {
            composable(route = CaseScreen.Main.route) {
                CasesView()
            }
        }
    }
}