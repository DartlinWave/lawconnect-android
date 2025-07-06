package com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch

import java.time.Instant
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository

@HiltViewModel
class CasesViewModel @Inject constructor(
    private val caseRepository: CaseRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(CasesUiState())
    val uiState: State<CasesUiState> = _uiState

    init {
        loadSuggestedCases()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredCases = filterCases(query, _uiState.value.selectedFilterType)
        )
    }

    fun onFilterTypeChange(filterType: FilterType) {
        _uiState.value = _uiState.value.copy(
            selectedFilterType = filterType,
            isDatePickerVisible = filterType == FilterType.DATE && _uiState.value.isDatePickerVisible.not(),
            filteredCases = filterCases(_uiState.value.searchQuery, filterType)
        )
    }

    fun toggleDatePickerVisibility(isVisible: Boolean) {
        _uiState.value = _uiState.value.copy(isDatePickerVisible = isVisible)
    }

    fun onStartDateSelected(date: Instant) {
        _uiState.value = _uiState.value.copy(
            startDate = date,
            filteredCases = filterCases(_uiState.value.searchQuery, _uiState.value.selectedFilterType)
        )
    }

    fun onEndDateSelected(date: Instant) {
        _uiState.value = _uiState.value.copy(
            endDate = date,
            filteredCases = filterCases(_uiState.value.searchQuery, _uiState.value.selectedFilterType)
        )
    }

    fun clearDateRange() {
        _uiState.value = _uiState.value.copy(
            startDate = null,
            endDate = null,
            filteredCases = filterCases(_uiState.value.searchQuery, _uiState.value.selectedFilterType)
        )
    }

    fun retry() {
        loadSuggestedCases()
    }

    // Function to load cases explicitly when navigating to the screen
    fun loadCases() {
        loadSuggestedCases()
    }

    private fun loadSuggestedCases() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            caseRepository.getSuggestedCases()
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { cases ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        allCases = cases,
                        filteredCases = filterCases(_uiState.value.searchQuery, _uiState.value.selectedFilterType)
                    )
                }
        }
    }

    private fun filterCases(query: String, filterType: FilterType): List<Case> {
        val cases = _uiState.value.allCases
        var filteredCases = cases

        // Apply search query filter if not blank
        if (query.isNotBlank()) {
            filteredCases = when (filterType) {
                FilterType.TITLE -> filteredCases.filter {
                    it.title.contains(query, ignoreCase = true)
                }
                FilterType.DATE -> filteredCases
            }
        }

        // Apply date filter if dates are set
        if (filterType == FilterType.DATE) {
            val startDate = _uiState.value.startDate
            val endDate = _uiState.value.endDate

            if (startDate != null) {
                filteredCases = filteredCases.filter { it.createdAt.isAfter(startDate) || it.createdAt.equals(startDate) }
            }

            if (endDate != null) {
                filteredCases = filteredCases.filter { it.createdAt.isBefore(endDate) || it.createdAt.equals(endDate) }
            }

            // Sort by date
            filteredCases = filteredCases.sortedBy { it.createdAt }
        }

        return filteredCases
    }
}

data class CasesUiState(
    val isLoading: Boolean = false,
    val allCases: List<Case> = emptyList(),
    val filteredCases: List<Case> = emptyList(),
    val searchQuery: String = "",
    val selectedFilterType: FilterType = FilterType.TITLE,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val isDatePickerVisible: Boolean = false,
    val error: String? = null
)

enum class FilterType {
    TITLE,
    DATE
}
