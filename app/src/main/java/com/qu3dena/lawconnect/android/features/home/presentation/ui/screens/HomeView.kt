package com.qu3dena.lawconnect.android.features.home.presentation.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels.CasesUiState
import com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels.HomeViewModel
import com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels.InvitedCasesUiState
import com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels.InvitedCase
import com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels.SuggestedCase
import com.qu3dena.lawconnect.android.shared.components.BrownActionButton

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val casesState = viewModel.casesState.value
    val invitedState = viewModel.invitedCasesState.value

    Column(modifier = Modifier.fillMaxSize()) {

        CaseSection(
            title = "Suggested Cases",
            cases = when (casesState) {
                is CasesUiState.Success -> casesState.cases
                else -> emptyList()
            },
            isLoading = casesState is CasesUiState.Loading,
            errorMessage = (casesState as? CasesUiState.Error)?.message,
            onRetry = { viewModel.retryCases() }
        ) { case: SuggestedCase ->
            CaseItem(case.title, case.status, Color.Blue)
        }

        CaseSection(
            title = "Invited Cases",
            cases = when (invitedState) {
                is InvitedCasesUiState.Success -> invitedState.invitedCases
                else -> emptyList()
            },
            isLoading = invitedState is InvitedCasesUiState.Loading,
            // Override error message to null to show "No cases found" when list is empty
            errorMessage = null,
            onRetry = { viewModel.retryInvitedCases() }
        ) { case: InvitedCase ->
            CaseItem(case.title, case.status, Color(0xFFA89D32))
        }
    }
}

@Composable
private fun CaseItem(
    titleText: String,
    statusText: String,
    color: Color = Color.Unspecified
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(190.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    titleText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Status: ")
                        withStyle(style = SpanStyle(color = color)) {
                            append(statusText)
                        }
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        AsyncImage(
            model = "https://media.istockphoto.com/id/1171169099/es/foto/hombre-con-brazos-cruzados-aislados-sobre-fondo-gris.jpg?s=612x612&w=0&k=20&c=8qDLKdLMm2i8DHXY6crX6a5omVh2IxqrOxJV2QGzgFg=",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopCenter)
                .offset(y = 20.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun <T> CaseSection(
    title: String,
    cases: List<T>,
    isLoading: Boolean,
    errorMessage: String? = null,
    onRetry: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }

    Divider(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
        thickness = 1.dp
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when {
            isLoading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading cases...")
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    BrownActionButton(
                        text = "Retry",
                        onClick = onRetry
                    )
                }
            }

            cases.isEmpty() -> {
                Text(
                    "No cases found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(cases) { case ->
                        itemContent(case)
                    }
                }
            }
        }
    }
}