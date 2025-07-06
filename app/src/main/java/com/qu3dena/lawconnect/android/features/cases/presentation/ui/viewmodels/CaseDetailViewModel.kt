package com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository

import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CaseDetailViewModel @Inject constructor(
    private val caseRepository: CaseRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(CaseDetailUiState())
    val uiState: State<CaseDetailUiState> = _uiState

    fun loadCaseDetail(caseId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            caseRepository.getCaseById(caseId)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { case ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        case = case,
                        error = null
                    )
                }
        }
    }
}

data class CaseDetailUiState(
    val isLoading: Boolean = false,
    val case: Case? = null,
    val error: String? = null
)
