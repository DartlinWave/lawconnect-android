package com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CasesView() {
    Column(modifier = Modifier.fillMaxSize()) {
        /*ListItem(
            modifier = Modifier.fillMaxWidth(),
            headlineContent = {
                Text(
                    text = "Case Tracking",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            supportingContent = {
                Text(
                    text = "View and update accepted cases",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )*/

        Text(
            text = "filters"
        )
    }
}