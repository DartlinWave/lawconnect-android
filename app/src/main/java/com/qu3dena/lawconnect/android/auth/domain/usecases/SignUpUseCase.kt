package com.qu3dena.lawconnect.android.auth.domain.usecases

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {
    fun invoke(username: String, role: String) = repository.signUp(
        username = username,
        password = role,
        role = "ROLE_LAWYER"
    )
}