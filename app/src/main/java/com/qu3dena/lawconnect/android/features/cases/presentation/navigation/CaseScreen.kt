package com.qu3dena.lawconnect.android.features.cases.presentation.navigation

import com.qu3dena.lawconnect.android.shared.navigation.Graph

const val DETAIL_ARGUMENT_CASE_ID = "caseId"

sealed class CaseScreen(val route: String) {
    object Main : CaseScreen("case_main")
    object Detail : CaseScreen("case_detail/{$DETAIL_ARGUMENT_CASE_ID}") {
        fun passCaseId(caseId: String): String {
            return this.route
                .replace(
                    oldValue = "{$DETAIL_ARGUMENT_CASE_ID}",
                    newValue = caseId
                )
        }
    }
    object Clients: CaseScreen("case_clients")
}