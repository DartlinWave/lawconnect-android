package com.qu3dena.lawconnect.android.core.presentation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.padding

import androidx.navigation.compose.rememberNavController

import com.qu3dena.lawconnect.android.core.navigation.SetupNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager
import com.qu3dena.lawconnect.android.core.navigation.NavigationCoordinator
import com.qu3dena.lawconnect.android.core.presentation.ui.components.AppTopBar
import com.qu3dena.lawconnect.android.core.presentation.ui.components.BottomBar

/**
 * Main screen composable that sets up the app's navigation structure.
 * 
 * This composable uses dependency injection to get feature navigation graphs,
 * following Clean Architecture principles where the core doesn't know about
 * specific features.
 */
@Composable
fun MainScreen(
    authSessionManager: AuthSessionManager,
    featureNavGraphs: List<FeatureNavGraph>
) {
    val isLoggedIn = authSessionManager.getIsLoggedInState().collectAsState(false)
    val username = authSessionManager.getUsernameLoggedState().collectAsState(null)

    val navController = rememberNavController()

    val navigationCoordinator = NavigationCoordinator(navController, authSessionManager)

    Scaffold(
        topBar = {
            if (isLoggedIn.value) {
                AppTopBar(username = username.value ?: "")
            }
        },
        bottomBar = {
            if (isLoggedIn.value)
                BottomBar(
                    navController = navController,
                    navigationCoordinator = navigationCoordinator
                )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 12.dp)
        ) {
            SetupNavGraph(
                navController = navController,
                padding = innerPadding,
                isLoggedIn = isLoggedIn.value,
                featureNavGraphs = featureNavGraphs,
                additionalParams = navigationCoordinator.prepareAdditionalParams()
            )
        }
    }
} 