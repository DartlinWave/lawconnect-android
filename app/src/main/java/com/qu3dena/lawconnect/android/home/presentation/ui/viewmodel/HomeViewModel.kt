package com.qu3dena.lawconnect.android.home.presentation.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.home.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.home.domain.model.Case
import com.qu3dena.lawconnect.android.home.domain.usecases.GetInvitedCasesUseCase
import com.qu3dena.lawconnect.android.home.domain.usecases.GetSuggestedCasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuggestedUiState {
    object Loading : SuggestedUiState()
    data class Success(val aCases: List<Case>) : SuggestedUiState()
    data class Error(val message: String) : SuggestedUiState()
}

sealed class InvitedUiState {
    object Loading : InvitedUiState()
    data class Success(val invitedCases: List<InvitedCase>) : InvitedUiState()
    data class Error(val message: String) : InvitedUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSuggestedCasesUseCase: GetSuggestedCasesUseCase,
    private val getInvitedCasesUseCase: GetInvitedCasesUseCase
) : ViewModel() {

    private val _suggestedState = mutableStateOf<SuggestedUiState>(SuggestedUiState.Loading)
    val suggestedState: State<SuggestedUiState> get() = _suggestedState

    private val _invitedState = mutableStateOf<InvitedUiState>(InvitedUiState.Loading)
    val invitedState: State<InvitedUiState> get() = _invitedState

    init {
        loadSuggestedCases()
        loadInvitedCases()
    }

    private fun loadSuggestedCases() {
        viewModelScope.launch {
            getSuggestedCasesUseCase.invoke()
                .onStart { _suggestedState.value = SuggestedUiState.Loading }
                .catch { throwable ->
                    _suggestedState.value =
                        SuggestedUiState.Error(throwable.localizedMessage ?: "Unknown error")
                }
                .collect { list ->
                    _suggestedState.value = SuggestedUiState.Success(list)
                }
        }
    }

    private fun loadInvitedCases() {
        viewModelScope.launch {
            getInvitedCasesUseCase.invoke()
                .onStart { _invitedState.value = InvitedUiState.Loading }
                .catch { throwable ->
                    _invitedState.value =
                        InvitedUiState.Error(throwable.localizedMessage ?: "Unknown error")
                }
                .collect { list ->
                    _invitedState.value = InvitedUiState.Success(list)
                }
        }
    }

    fun retrySuggested() {
        loadSuggestedCases()
    }

    fun retryInvited() {
        loadInvitedCases()
    }
}