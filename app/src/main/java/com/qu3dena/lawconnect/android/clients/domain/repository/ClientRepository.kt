package com.qu3dena.lawconnect.android.clients.domain.repository

import com.qu3dena.lawconnect.android.clients.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    fun getClients(): Flow<List<Client>>
}