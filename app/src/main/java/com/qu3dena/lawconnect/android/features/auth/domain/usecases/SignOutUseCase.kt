package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {
    suspend fun invoke() {
        repository.signOut().collect { }
    }
}