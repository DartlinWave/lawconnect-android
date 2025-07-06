package com.qu3dena.lawconnect.android.shared.services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface RawRetrofitService {
    @GET
    suspend fun getRaw(@Url url: String): ResponseBody

    @POST
    suspend fun postRaw(
        @Url url: String,
        @Body body: RequestBody
    ): ResponseBody

    @PUT
    suspend fun putRaw(
        @Url url: String,
        @Body body: RequestBody
    ): ResponseBody

    @DELETE
    suspend fun deleteRaw(@Url url: String): ResponseBody
}