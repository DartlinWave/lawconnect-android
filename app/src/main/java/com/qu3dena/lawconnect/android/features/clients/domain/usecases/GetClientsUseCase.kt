package com.qu3dena.lawconnect.android.features.clients.domain.usecases

import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository

class GetClientsUseCase(private val repository: ClientRepository) {

    fun invoke() = repository.getClients()
}