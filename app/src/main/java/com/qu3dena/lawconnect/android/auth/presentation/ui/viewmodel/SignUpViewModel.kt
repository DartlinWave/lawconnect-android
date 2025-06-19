package com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.auth.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    object Success : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    // Paso 1
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPass by mutableStateOf("")

    // Paso 2
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var description by mutableStateOf("")
    var specialties by mutableStateOf<List<String>>(emptyList())

    private val _uiState = mutableStateOf<SignUpUiState>(SignUpUiState.Idle)
    val uiState: State<SignUpUiState> = _uiState

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.invoke(username, password)
                .onStart { _uiState.value = SignUpUiState.Loading }
                .catch { e ->

                    val bodyText = (e as? HttpException)
                        ?.response()?.errorBody()?.string()
                        .orEmpty()

                    val finalMsg = when {
                        bodyText.isNotBlank() -> bodyText
                        !e.message.isNullOrBlank() -> e.message!!
                        else -> "Unknown error"
                    }

                    _uiState.value = SignUpUiState.Error(finalMsg)
                }
                .collect {
                    _uiState.value = SignUpUiState.Success
                }
        }
    }

    fun resetState() {
        _uiState.value = SignUpUiState.Idle
    }
}
