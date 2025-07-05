package com.qu3dena.lawconnect.android.features.cases.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels.CasesViewModel
import com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels.FilterType

@Composable
fun CasesView(
    viewModel: CasesViewModel = hiltViewModel(),
    onCaseClick: ((String) -> Unit)? = null
) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadCases()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FilterSection(
            viewModel = viewModel,
            searchQuery = uiState.searchQuery,
            selectedFilterType = uiState.selectedFilterType,
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            isDatePickerVisible = uiState.isDatePickerVisible,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onFilterTypeChange = viewModel::onFilterTypeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                ErrorSection(
                    error = uiState.error.toString(),
                    onRetry = viewModel::retry
                )
            }

            uiState.filteredCases.isEmpty() && uiState.allCases.isEmpty() -> {
                EmptyStateSection()
            }

            uiState.filteredCases.isEmpty() && uiState.searchQuery.isNotBlank() -> {
                NoResultsSection()
            }

            else -> {
                CasesList(
                    cases = uiState.filteredCases,
                    onCaseClick = onCaseClick
                )
            }
        }
    }
}

@Composable
private fun FilterSection(
    viewModel: CasesViewModel,
    searchQuery: String,
    selectedFilterType: FilterType,
    startDate: Instant?,
    endDate: Instant?,
    isDatePickerVisible: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onFilterTypeChange: (FilterType) -> Unit
) {
    Column {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text("Search cases...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilterType == FilterType.TITLE,

                onClick = {
                    onFilterTypeChange(FilterType.TITLE)
                },

                label = {
                    Text("By Title")
                }
            )

            FilterChip(
                selected = selectedFilterType == FilterType.DATE,

                onClick = {
                    onFilterTypeChange(FilterType.DATE)
                    viewModel.toggleDatePickerVisibility(true)
                },

                label = {
                    Text("By Date")
                },

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
        }

        if (selectedFilterType == FilterType.DATE) {
            Spacer(modifier = Modifier.height(12.dp))
            DateRangeSection(
                startDate = startDate,
                endDate = endDate,
                onStartDateClick = { viewModel.toggleDatePickerVisibility(true) },
                onEndDateClick = { viewModel.toggleDatePickerVisibility(true) },
                onClearDates = viewModel::clearDateRange
            )
        }

        if (isDatePickerVisible) {
            DatePickerDialog(
                onDateSelected = { date ->
                    if (startDate == null || (endDate != null)) {
                        viewModel.onStartDateSelected(date)
                    } else {
                        viewModel.onEndDateSelected(date)
                    }
                    viewModel.toggleDatePickerVisibility(false)
                },
                onDismiss = { viewModel.toggleDatePickerVisibility(false) }
            )
        }
    }
}

@Composable
private fun DateRangeSection(
    startDate: Instant?,
    endDate: Instant?,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    onClearDates: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Date Range Filter",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Start Date
                OutlinedButton(
                    onClick = onStartDateClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (startDate != null) {
                            startDate.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                        } else {
                            "Start Date"
                        }
                    )
                }

                // End Date
                OutlinedButton(
                    onClick = onEndDateClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (endDate != null) {
                            endDate.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                        } else {
                            "End Date"
                        }
                    )
                }
            }

            if (startDate != null || endDate != null) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onClearDates,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear Dates")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    onDateSelected: (Instant) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelected(Instant.ofEpochMilli(millis))
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun CasesList(cases: List<Case>, onCaseClick: ((String) -> Unit)? = null) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cases) { case ->
            CaseCard(
                case = case,
                onClick = { onCaseClick?.invoke(case.id.toString()) }
            )
        }
    }
}

@Composable
private fun CaseCard(case: Case, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = case.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Client: ${case.clientId.toString().take(8)}...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = case.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Blue
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = case.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Created: ${
                            case.createdAt.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                        }",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorSection(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error loading cases",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyStateSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No suggested cases found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Check back later for new case suggestions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun NoResultsSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No results found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Try adjusting your search or filters",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}