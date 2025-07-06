package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(username: String, password: String, role: String = "ROLE_LAWYER") = repository.signUp(
        username = username,
        password = password,
        role = role
    )
}