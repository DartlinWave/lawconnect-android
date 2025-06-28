package com.qu3dena.lawconnect.android.features.clients.data.remote

import com.qu3dena.lawconnect.android.features.clients.data.model.ClientDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ClientApiService {

    @GET("cases/lawyer/{lawyerId}")
    suspend fun getClients(
        @Path("lawyerId") lawyerId: String,
    ): List<ClientDto>
}
