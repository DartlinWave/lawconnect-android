package com.qu3dena.lawconnect.android.features.profile.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Profile screen composable.
 * 
 * Simple profile view that displays user information and provides
 * a sign out button. The sign out functionality is handled by the
 * callback passed from the core.
 * 
 * Clean Architecture principles:
 * - Pure UI component with no business logic
 * - Receives callbacks from parent (core)
 * - No direct dependencies on other features
 */
@Composable
fun ProfileView(
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile Screen")
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}