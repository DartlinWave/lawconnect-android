package com.qu3dena.lawconnect.android.features.auth.domain.repository

import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileRequest
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(username: String, password: String, role: String): Flow<SignUpResponse>

    fun signIn(username: String, password: String): Flow<SignInResponse>

    fun signOut(): Flow<Unit>

    fun getToken(): Flow<String?>

    fun getUsername(): Flow<String?>

    fun createLawyerProfile(lawyerProfileRequest: LawyerProfileRequest): Flow<LawyerProfileResponse>
}