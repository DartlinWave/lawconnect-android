package com.qu3dena.lawconnect.android.clients.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.SpanStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qu3dena.lawconnect.android.clients.domain.model.Client
import com.qu3dena.lawconnect.android.clients.presentation.ui.viewmodel.ClientsViewModel
import com.qu3dena.lawconnect.android.clients.presentation.ui.viewmodel.ClientUiState
import com.qu3dena.lawconnect.android.core.ui.components.BrownActionButton
import com.qu3dena.lawconnect.android.core.ui.components.GrayActionButton

@Composable
fun ClientsView(
    viewModel: ClientsViewModel = hiltViewModel()
) {
    val state = viewModel.clientsState.value

    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
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
        )

        when (state) {
            is ClientUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is ClientUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is ClientUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.aCases) { client ->
                        ClientItem(client)
                    }
                }
            }
        }
    }
}

@Composable
fun ClientItem(
    client: Client,
    onFollowUp: () -> Unit = {},
    onUpdate: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = client.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        /*TODO: Search and set the customer name*/
                        text = "Customer name",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        append("Status: ")
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append(client.status)
                        }
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Start date: ${client.createdAt}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BrownActionButton(
                    onClick = onFollowUp,
                    text = "Follow-up",
                    modifier = Modifier.weight(1f)
                )
                GrayActionButton(
                    onClick = onUpdate,
                    text = "Update",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}