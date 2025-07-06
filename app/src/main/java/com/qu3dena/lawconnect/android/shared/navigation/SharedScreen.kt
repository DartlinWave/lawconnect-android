package com.qu3dena.lawconnect.android.shared.navigation

sealed class SharedScreen(val route: String) {
    object Clients : SharedScreen("shared_clients")
}