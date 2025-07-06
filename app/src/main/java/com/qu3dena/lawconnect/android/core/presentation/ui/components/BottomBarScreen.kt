package com.qu3dena.lawconnect.android.core.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.qu3dena.lawconnect.android.core.navigation.Graph
import com.qu3dena.lawconnect.android.shared.navigation.SharedScreen

/**
 * Model representing bottom bar screen items.
 * 
 * This sealed class defines all the screens that appear in the bottom navigation bar.
 * Each screen has a route, title, and icon for navigation purposes.
 */
sealed class BottomBarScreen(
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
        route = SharedScreen.Clients.route,
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