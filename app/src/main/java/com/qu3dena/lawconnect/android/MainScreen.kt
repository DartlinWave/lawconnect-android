package com.qu3dena.lawconnect.android

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.AuthViewModel
import com.qu3dena.lawconnect.android.navigation.Graph
import com.qu3dena.lawconnect.android.navigation.SetupNavGraph

@Composable
fun MainScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState(false)
    val username = authViewModel.username.collectAsState("")

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            if (isLoggedIn.value) {
                AppTopBar(username = username.value.toString())
            }
        },
        bottomBar = {
            if (isLoggedIn.value)
                BottomBar(navController = navController)
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
                authViewModel = authViewModel
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
fun BottomBar(navController: NavHostController) {
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
                navController = navController
            )
        }
    }
}

@Composable
private fun RowScope.AddBottomBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun AppTopBar(username: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp
            )
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