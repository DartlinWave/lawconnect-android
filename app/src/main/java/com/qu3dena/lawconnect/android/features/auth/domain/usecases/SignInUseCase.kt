package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(username: String, password: String) = repository.signIn(
        username = username,
        password = password
    )
}