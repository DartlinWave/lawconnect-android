package com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens

import androidx.compose.material3.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight

import androidx.hilt.navigation.compose.hiltViewModel

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.shared.components.GreenActionButton
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels.CaseDetailViewModel
import com.qu3dena.lawconnect.android.shared.components.RedActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailView(
    caseId: String,
    onBackPressed: () -> Unit,
    viewModel: CaseDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LaunchedEffect(caseId) {
        viewModel.loadCaseDetail(caseId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.error != null -> {
                ErrorSection(
                    error = uiState.error!!,
                    onRetry = { viewModel.loadCaseDetail(caseId) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.case != null -> {
                CaseDetailContent(
                    case = uiState.case!!,
                    onApplyClick = {
                        viewModel.submitApplicationToCase()
                        onBackPressed()
                    }
                )
            }
        }
    }
}

@Composable
private fun CaseDetailContent(
    case: Case,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = case.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Case ID: ${case.id}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = case.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        GreenActionButton(
            text = "Apply to Case",
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ErrorSection(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error loading case details",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}