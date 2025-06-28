package com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetSessionStateUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetUsernameUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val getSessionStateUseCase: GetSessionStateUseCase
) : ViewModel() {

    val username: StateFlow<String?> = getUsernameUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val isLoggedIn: StateFlow<Boolean> =
        getSessionStateUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }
}