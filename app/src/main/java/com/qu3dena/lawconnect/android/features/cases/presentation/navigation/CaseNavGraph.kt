package com.qu3dena.lawconnect.android.features.cases.presentation.navigation

import androidx.navigation.navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CaseClientsView

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CasesView
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens.CaseDetailView

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
                    onCaseClick = { caseId: String ->
                        navController.navigate(CaseScreen.Detail.passCaseId(caseId))
                    }
                )
            }

            composable(
                route = CaseScreen.Detail.route,
                arguments = listOf(
                    navArgument(DETAIL_ARGUMENT_CASE_ID) {
                        type = NavType.StringType
                    }
                )

            ) { backStackEntry ->
                val caseId = backStackEntry.arguments?.getString("caseId") ?: ""
                CaseDetailView(
                    caseId = caseId,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = CaseScreen.Clients.route) {
                CaseClientsView()
            }
        }
    }
}