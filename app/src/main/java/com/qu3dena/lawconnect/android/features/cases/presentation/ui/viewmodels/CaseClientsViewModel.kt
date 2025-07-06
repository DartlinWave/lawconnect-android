package com.qu3dena.lawconnect.android.features.cases.presentation.ui.viewmodels

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.usecases.GetCaseClientsUseCase

sealed class ClientUiState {
    object Loading : ClientUiState()
    data class Success(val aCases: List<Case>) : ClientUiState()
    data class Error(val message: String) : ClientUiState()
}

@HiltViewModel
class CaseClientsViewModel @Inject constructor(
    private val getCaseClientsUseCase: GetCaseClientsUseCase
) : ViewModel() {

    private val _clientsState = mutableStateOf<ClientUiState>(ClientUiState.Loading)
    val clientsState: State<ClientUiState> get() = _clientsState

    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            getCaseClientsUseCase.invoke()
                .onStart { _clientsState.value = ClientUiState.Loading }
                .catch { throwable ->
                    _clientsState.value =
                        ClientUiState.Error(throwable.localizedMessage ?: "Unknown error")
                }
                .collect { list ->
                    _clientsState.value = ClientUiState.Success(list)
                }
        }
    }
}