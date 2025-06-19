package com.qu3dena.lawconnect.android.auth.data.repository

import com.qu3dena.lawconnect.android.auth.data.model.SignInRequestDto
import com.qu3dena.lawconnect.android.auth.data.model.SignUpRequestDto
import com.qu3dena.lawconnect.android.auth.data.model.SignUpResponseDto
import com.qu3dena.lawconnect.android.auth.data.source.local.AuthPreferences
import com.qu3dena.lawconnect.android.auth.data.source.remote.AuthApiService
import com.qu3dena.lawconnect.android.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.auth.domain.model.SignUpResponse
import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override fun signUp(username: String, password: String, role: String): Flow<SignUpResponse> = flow {
        val request = SignUpRequestDto(username, password, role)
        val response = apiService.signUp(request)
        emit(response.toSignUpResponse())
    }.flowOn(Dispatchers.IO)

    override fun signIn(username: String, password: String): Flow<SignInResponse> = flow {
        val request = SignInRequestDto(username, password)
        val response = apiService.signIn(request)
        authPreferences.saveToken(response.token)
        emit(response.toSignInResponse())
    }.flowOn(Dispatchers.IO)
}