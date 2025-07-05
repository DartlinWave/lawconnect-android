package com.qu3dena.lawconnect.android.features.cases.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CasesView
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CaseDetailView
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

sealed class CaseScreen(val route: String) {
    object Main : CaseScreen("case_main")
    object Detail : CaseScreen("case_detail/{caseId}") {
        fun createRoute(caseId: String) = "case_detail/$caseId"
    }
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
                CasesView(
                    onCaseClick = { caseId ->
                        navController.navigate(CaseScreen.Detail.createRoute(caseId))
                    }
                )
            }

            composable(route = CaseScreen.Detail.route) { backStackEntry ->
                val caseId = backStackEntry.arguments?.getString("caseId") ?: ""
                CaseDetailView(
                    caseId = caseId,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}