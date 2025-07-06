package com.qu3dena.lawconnect.android.features.home.presentation.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.shared.contracts.CaseLookupService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data classes for suggested and invited cases.
data class SuggestedCase(val title: String, val status: String)
data class InvitedCase(val title: String, val status: String)

sealed class CasesUiState {
    object Loading : CasesUiState()
    data class Success(val cases: List<SuggestedCase>) : CasesUiState()
    data class Error(val message: String) : CasesUiState()
}

sealed class InvitedCasesUiState {
    object Loading : InvitedCasesUiState()
    data class Success(val invitedCases: List<InvitedCase>) : InvitedCasesUiState()
    data class Error(val message: String) : InvitedCasesUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val caseLookupService: CaseLookupService
) : ViewModel() {

    private val _casesState = mutableStateOf<CasesUiState>(CasesUiState.Loading)
    val casesState: State<CasesUiState> get() = _casesState

    private val _invitedCasesState =
        mutableStateOf<InvitedCasesUiState>(InvitedCasesUiState.Loading)
    val invitedCasesState: State<InvitedCasesUiState> get() = _invitedCasesState

    init {
        loadCases()
        loadInvitedCases()
    }

     fun loadCases() {
        viewModelScope.launch {
            _casesState.value = CasesUiState.Loading

            try {
                val titles = caseLookupService.getCaseTitles()
                val statuses = caseLookupService.getCaseStatuses()
                val cases = titles.zip(statuses) { t, s -> SuggestedCase(t, s) }

                _casesState.value = if (cases.isNotEmpty()) {
                    CasesUiState.Success(cases)
                } else {
                    CasesUiState.Error("No cases found")
                }
            } catch (e: Exception) {
                _casesState.value = CasesUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun loadInvitedCases() {
        viewModelScope.launch {
            _invitedCasesState.value = InvitedCasesUiState.Loading
            try {
                val titles = caseLookupService.getInvitedCaseTitles()
                val statuses = caseLookupService.getInvitedCaseStatuses()
                val invitedCases = titles.zip(statuses) { t, s -> InvitedCase(t, s) }

                _invitedCasesState.value = if (invitedCases.isNotEmpty()) {
                    InvitedCasesUiState.Success(invitedCases)
                } else {
                    InvitedCasesUiState.Error("No invited cases found")
                }
            } catch (e: Exception) {
                _invitedCasesState.value =
                    InvitedCasesUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun retryCases() {
        loadCases()
    }

    fun retryInvitedCases() {
        loadInvitedCases()
    }
}