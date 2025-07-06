package com.qu3dena.lawconnect.android.core.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import com.qu3dena.lawconnect.android.core.navigation.NavigationCoordinator

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
    // Delegate the selected state determination to the NavigationCoordinator
    // This follows Clean Architecture by centralizing navigation logic
    val selected = navigationCoordinator.isRouteSelected(screen.route, currentDestination)

    NavigationBarItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        },
        selected = selected,
        onClick = {
            navigationCoordinator.navigateToBottomBarDestination(screen.route)
        }
    )
}
