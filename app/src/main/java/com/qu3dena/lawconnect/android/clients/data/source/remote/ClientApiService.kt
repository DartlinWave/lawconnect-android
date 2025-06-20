package com.qu3dena.lawconnect.android.clients.data.source.remote

import com.qu3dena.lawconnect.android.clients.data.model.ClientDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ClientApiService {

    @GET("cases/lawyer/{lawyerId}")
    suspend fun getClients(
        @Path("lawyerId") lawyerId: String,
    ): List<ClientDto>
}
