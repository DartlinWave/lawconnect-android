package com.qu3dena.lawconnect.android.core.presentation.components

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.qu3dena.lawconnect.android.core.navigation.NavigationCoordinator
import com.qu3dena.lawconnect.android.core.presentation.components.BottomBarScreen

/**
 * Bottom navigation bar component.
 * 
 * This component displays the main navigation options at the bottom of the screen.
 * It shows different screens that the user can navigate to and handles the
 * current selection state.
 */
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
            BottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navigationCoordinator = navigationCoordinator
            )
        }
    }
} 