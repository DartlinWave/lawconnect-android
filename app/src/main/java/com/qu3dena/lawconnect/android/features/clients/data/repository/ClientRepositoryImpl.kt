package com.qu3dena.lawconnect.android.features.clients.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.clients.data.model.toDomain
import com.qu3dena.lawconnect.android.features.clients.data.remote.ClientApiService
import com.qu3dena.lawconnect.android.features.clients.domain.model.Client
import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ClientRepositoryImpl @Inject constructor(
    private val api: ClientApiService,
    private val authPreferences: AuthPreferences
) : ClientRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getClients(): Flow<List<Client>> = flow {
        val userId = authPreferences.userIdFlow.first()
            ?: throw IllegalStateException("The user ID is not available. Please log in first.")

        val clients = api.getClients(lawyerId = userId)
            .map { it.toDomain() }

        emit(clients)
    }.flowOn(Dispatchers.IO)
}