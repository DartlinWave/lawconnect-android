package com.qu3dena.lawconnect.android.clients.presentation.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.clients.domain.model.Client
import com.qu3dena.lawconnect.android.clients.domain.usecases.GetClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ClientUiState {
    object Loading : ClientUiState()
    data class Success(val aCases: List<Client>) : ClientUiState()
    data class Error(val message: String) : ClientUiState()
}

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getClientsUseCase: GetClientsUseCase
) : ViewModel() {

    private val _clientsState = mutableStateOf<ClientUiState>(ClientUiState.Loading)
    val clientsState: State<ClientUiState> get() = _clientsState

    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            getClientsUseCase.invoke()
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