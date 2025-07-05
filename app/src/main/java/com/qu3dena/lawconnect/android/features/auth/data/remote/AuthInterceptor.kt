package com.qu3dena.lawconnect.android.features.auth.data.remote

import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authPreferences: AuthPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the token from AuthPreferences
        val token: String? = runBlocking {
            authPreferences.tokenFlow.first()
        }

        val request = chain.request().newBuilder().apply {
            token?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        return chain.proceed(request)
    }
}
