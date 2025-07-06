package com.qu3dena.lawconnect.android.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Top bar component that displays the current user's username.
 * 
 * This component is shown when the user is logged in and provides
 * a consistent header across the application.
 */
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