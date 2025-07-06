package com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf


import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SubmitApplicationToCaseUseCase

import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@HiltViewModel
class CaseDetailViewModel @Inject constructor(
    private val caseRepository: CaseRepository,
    private val submitApplicationToCaseUseCase: SubmitApplicationToCaseUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(CaseDetailUiState())
    val uiState: State<CaseDetailUiState> = _uiState

    private val _applicationToCaseUiState = mutableStateOf(ApplicationToCaseUiState())
    val applicationToCaseUiState: State<ApplicationToCaseUiState> = _applicationToCaseUiState

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

    fun submitApplicationToCase() {

        val caseId = _uiState.value.case?.id

        if (caseId == null) {
            _uiState.value = _uiState.value.copy(
                error = "Case ID is null. Cannot submit application."
            )
            return
        }

        _applicationToCaseUiState.value = _applicationToCaseUiState.value.copy(
            isSubmitting = true, error = null
        )

        viewModelScope.launch {
            submitApplicationToCaseUseCase.invoke(caseId)
                .catch { _ ->
                    _applicationToCaseUiState.value = _applicationToCaseUiState.value.copy(
                        isSubmitting = false,
                        error = "Failed to submit application. Please try again."
                    )
                }
                .collect {
                    _applicationToCaseUiState.value = _applicationToCaseUiState.value.copy(
                        isSubmitting = false
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

data class ApplicationToCaseUiState(
    val isSubmitting: Boolean = false,
    val error: String? = null
)
