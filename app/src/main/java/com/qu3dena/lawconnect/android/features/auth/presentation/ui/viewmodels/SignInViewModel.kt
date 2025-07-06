package com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels

import retrofit2.HttpException
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class SignInUiState {
    object Idle : SignInUiState()
    object Loading : SignInUiState()
    object Success : SignInUiState()
    data class Error(val message: String) : SignInUiState()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    private val _uiState = mutableStateOf<SignInUiState>(SignInUiState.Idle)
    val uiState: State<SignInUiState> = _uiState

    fun signIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            signInUseCase.invoke(username, password)
                .onStart { _uiState.value = SignInUiState.Loading }
                .catch { e ->
                    val msg = (e as? HttpException)
                        ?.response()?.errorBody()?.string()
                        ?.takeIf { it.isNotBlank() }
                        ?: e.message.orEmpty().ifBlank { "Unknown Error" }
                    _uiState.value = SignInUiState.Error(msg)
                }
                .collect {
                    _uiState.value = SignInUiState.Success
                    onSuccess()
                }
        }
    }

    fun resetState() {
        _uiState.value = SignInUiState.Idle
    }
}