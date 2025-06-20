package com.qu3dena.lawconnect.android.auth.domain.usecases

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {
    suspend fun invoke() {
        repository.signOut().collect { }
    }
}