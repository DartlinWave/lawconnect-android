package com.qu3dena.lawconnect.android.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels.AuthViewModel
import com.qu3dena.lawconnect.android.core.navigation.Graph
import com.qu3dena.lawconnect.android.core.navigation.NavigationCoordinator
import com.qu3dena.lawconnect.android.core.navigation.SetupNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import javax.inject.Inject

/**
 * Main screen composable that sets up the app's navigation structure.
 * 
 * This composable uses dependency injection to get feature navigation graphs,
 * following Clean Architecture principles where the core doesn't know about
 * specific features.
 */
@Composable
fun MainScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    featureNavGraphs: List<FeatureNavGraph>
) {
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState(false)
    val username = authViewModel.username.collectAsState("")

    val navController = rememberNavController()
    
    // Create navigation coordinator to handle all navigation logic
    val navigationCoordinator = NavigationCoordinator(navController, authViewModel)

    Scaffold(
        topBar = {
            if (isLoggedIn.value) {
                AppTopBar(username = username.value.toString())
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

private sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = Graph.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )

    object Clients : BottomBarScreen(
        route = Graph.Clients.route,
        title = "Clients",
        icon = Icons.Default.Face
    )

    object Cases : BottomBarScreen(
        route = Graph.Cases.route,
        title = "Cases",
        icon = Icons.Default.MailOutline
    )

    object Profile : BottomBarScreen(
        route = Graph.Profile.route,
        title = "Profile",
        icon = Icons.Default.Person
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    navigationCoordinator: NavigationCoordinator
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Clients,
        BottomBarScreen.Cases,
        BottomBarScreen.Profile
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddBottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navigationCoordinator = navigationCoordinator
            )
        }
    }
}

@Composable
private fun RowScope.AddBottomBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navigationCoordinator: NavigationCoordinator
) {
    NavigationBarItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navigationCoordinator.navigateToBottomBarDestination(screen.route)
        }
    )
}

@Composable
fun AppTopBar(username: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp)
            .background(Color.White)
            .padding(16.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = username,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp
        )
    }
}