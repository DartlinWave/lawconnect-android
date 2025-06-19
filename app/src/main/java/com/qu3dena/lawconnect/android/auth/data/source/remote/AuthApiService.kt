package com.qu3dena.lawconnect.android.auth.data.source.remote

import com.qu3dena.lawconnect.android.auth.data.model.SignInRequestDto
import com.qu3dena.lawconnect.android.auth.data.model.SignInResponseDto
import com.qu3dena.lawconnect.android.auth.data.model.SignUpRequestDto
import com.qu3dena.lawconnect.android.auth.data.model.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("authentication/sign-up")
    suspend fun signUp(
        @Body request: SignUpRequestDto
    ): SignUpResponseDto

    @POST("authentication/sign-in")
    suspend fun signIn(
        @Body request: SignInRequestDto
    ): SignInResponseDto
}