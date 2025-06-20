package com.qu3dena.lawconnect.android.auth.domain.usecases

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GetSessionStateUseCase(
    private val repository: AuthRepository
) {
    fun invoke(): Flow<Boolean> =
        repository.getTokenFlow().map { it != null }.distinctUntilChanged()
}