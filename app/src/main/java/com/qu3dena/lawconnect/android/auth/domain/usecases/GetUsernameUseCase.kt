package com.qu3dena.lawconnect.android.auth.domain.usecases

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetUsernameUseCase(private val repository: AuthRepository) {

    fun invoke(): Flow<String?> = repository.getUsernameFlow()
}