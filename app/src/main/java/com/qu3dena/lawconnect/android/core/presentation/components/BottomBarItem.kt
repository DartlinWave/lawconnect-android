package com.qu3dena.lawconnect.android.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.qu3dena.lawconnect.android.core.navigation.NavigationCoordinator
import com.qu3dena.lawconnect.android.core.presentation.components.BottomBarScreen

/**
 * Individual bottom bar item component.
 * 
 * This component represents a single navigation item in the bottom bar.
 * It handles the visual state (selected/unselected) and navigation logic.
 */
@Composable
fun RowScope.BottomBarItem(
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