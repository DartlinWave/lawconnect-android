package com.qu3dena.lawconnect.android.auth.domain.usecases

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {
    fun invoke(username: String, password: String) = repository.signIn(
        username = username,
        password = password
    )
}