package com.qu3dena.lawconnect.android.clients.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ClientsView() {
    Column {
        Text(
            text = "Clients View",
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
        )
    }
}