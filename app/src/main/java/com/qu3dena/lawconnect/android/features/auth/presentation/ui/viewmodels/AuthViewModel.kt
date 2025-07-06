package com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels

import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted

import dagger.hilt.android.lifecycle.HiltViewModel

import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SignOutUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetUsernameUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetSessionStateUseCase

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val getSessionStateUseCase: GetSessionStateUseCase
) : ViewModel(), AuthSessionManager {

    private val username: StateFlow<String?> = getUsernameUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val isLoggedIn: StateFlow<Boolean> =
        getSessionStateUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }

    override fun getIsLoggedInState(): StateFlow<Boolean> = isLoggedIn

    override fun getUsernameLoggedState(): StateFlow<String?> = username
}