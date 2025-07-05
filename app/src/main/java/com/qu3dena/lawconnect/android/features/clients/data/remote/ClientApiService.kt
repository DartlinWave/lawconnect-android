package com.qu3dena.lawconnect.android.features.clients.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.clients.data.model.ClientDto
import com.qu3dena.lawconnect.android.features.clients.data.model.toDomain
import com.qu3dena.lawconnect.android.features.clients.domain.model.Client
import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository
import com.qu3dena.lawconnect.android.shared.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ClientApiService @Inject constructor(
    private val api: ApiService,
    private val authPreferences: AuthPreferences
) : ClientRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getClients(): Flow<List<Client>> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()
            if (userId == null) {
                throw IllegalStateException("The user ID is not available. Please log in first.")
            }

            val clients = api.get<List<ClientDto>>("cases/lawyer/$userId")
                .map { it.toDomain() }

            emit(clients)
        } catch (_: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
