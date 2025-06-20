package com.qu3dena.lawconnect.android.clients.domain.usecases

import com.qu3dena.lawconnect.android.clients.domain.repository.ClientRepository

class GetClientsUseCase(private val repository: ClientRepository) {

    fun invoke() = repository.getClients()
}