// kotlin
package com.qu3dena.lawconnect.android.features.auth.data

import javax.inject.Inject
import javax.inject.Singleton

import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted

import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SignOutUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetUsernameUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.GetSessionStateUseCase

@Singleton
class AuthSessionService @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val getSessionStateUseCase: GetSessionStateUseCase
) : AuthSessionManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun signOut() {
        scope.launch {
            signOutUseCase.invoke()
        }
    }

    override fun getIsLoggedInState(): StateFlow<Boolean> = 
        getSessionStateUseCase.invoke().stateIn(scope, SharingStarted.Eagerly, false)

    override fun getUsernameLoggedState(): StateFlow<String?> = 
        getUsernameUseCase.invoke().stateIn(scope, SharingStarted.Eagerly, null)
}