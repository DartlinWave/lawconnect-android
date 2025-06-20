package com.qu3dena.lawconnect.android.auth.domain.repository

import com.qu3dena.lawconnect.android.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.auth.domain.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(username: String, password: String, role: String): Flow<SignUpResponse>

    fun signIn(username: String, password: String): Flow<SignInResponse>

    fun getTokenFlow(): Flow<String?>

    fun signOut(): Flow<Unit>
}