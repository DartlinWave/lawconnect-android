package com.qu3dena.lawconnect.android.auth.data.source.remote

import com.qu3dena.lawconnect.android.auth.data.source.local.AuthPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authPreferences: AuthPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // We are blocking until we get the token from the flow
        val token: String? = runBlocking {
            authPreferences.tokenFlow.first()
        }

        val request = chain.request().newBuilder().apply {
            token?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        return chain.proceed(request)
    }
}