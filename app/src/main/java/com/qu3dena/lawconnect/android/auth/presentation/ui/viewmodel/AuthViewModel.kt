package com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.auth.domain.usecases.GetSessionStateUseCase
import com.qu3dena.lawconnect.android.auth.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getSessionStateUseCase: GetSessionStateUseCase
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> =
        getSessionStateUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
        }
    }
}