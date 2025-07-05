package com.qu3dena.lawconnect.android.features.auth.data.remote

import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignInRequestDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignInResponseDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignUpRequestDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignUpResponseDto
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignUpResponse
import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import com.qu3dena.lawconnect.android.shared.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthApiService @Inject constructor(
    private val api: ApiService,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override fun signUp(username: String, password: String, role: String): Flow<SignUpResponse> = flow {
        try {
            val request = SignUpRequestDto(username, password, role)
            val response = api.post<SignUpRequestDto, SignUpResponseDto>("authentication/sign-up", request)
            emit(response.toSignUpResponse())
        } catch (_: Exception) {
            throw Exception("Registration failed")
        }
    }.flowOn(Dispatchers.IO)

    override fun signIn(username: String, password: String): Flow<SignInResponse> = flow {
        try {
            val request = SignInRequestDto(username, password)
            val response = api.post<SignInRequestDto, SignInResponseDto>("authentication/sign-in", request)

            // Save authentication data
            authPreferences.saveToken(response.token)
            authPreferences.saveUsername(response.username)

            emit(response.toSignInResponse())
        } catch (e: Exception) {
            throw Exception("Login failed: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    override fun signOut(): Flow<Unit> = flow {
        try {
            authPreferences.clearToken()
            authPreferences.clearUsername()
            emit(Unit)
        } catch (_: Exception) {
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)

    override fun getTokenFlow(): Flow<String?> =
        authPreferences.tokenFlow

    override fun getUsernameFlow(): Flow<String?> =
        authPreferences.usernameFlow
}
