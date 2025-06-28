package com.qu3dena.lawconnect.android.features.auth.domain.repository

import com.qu3dena.lawconnect.android.features.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(username: String, password: String, role: String): Flow<SignUpResponse>

    fun signIn(username: String, password: String): Flow<SignInResponse>

    fun getTokenFlow(): Flow<String?>

    fun signOut(): Flow<Unit>

    fun getUsernameFlow(): Flow<String?>
}