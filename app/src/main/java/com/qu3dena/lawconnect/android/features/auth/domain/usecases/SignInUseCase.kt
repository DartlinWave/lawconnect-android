package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {
    fun invoke(username: String, password: String) = repository.signIn(
        username = username,
        password = password
    )
}