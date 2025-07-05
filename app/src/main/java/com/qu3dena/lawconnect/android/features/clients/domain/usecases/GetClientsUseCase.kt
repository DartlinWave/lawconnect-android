package com.qu3dena.lawconnect.android.features.clients.domain.usecases

import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    operator fun invoke() = repository.getClients()
}