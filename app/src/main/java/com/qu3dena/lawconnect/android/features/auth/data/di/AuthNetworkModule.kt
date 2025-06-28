package com.qu3dena.lawconnect.android.features.auth.data.di

import android.content.Context
import com.qu3dena.lawconnect.android.features.auth.data.source.remote.AuthApiService
import com.qu3dena.lawconnect.android.features.auth.data.source.remote.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    // Provides a singleton instance of AuthPreferences using the application context.
    @Provides
    @Singleton
    fun provideAuthPreferences(
        @ApplicationContext ctx: Context
    ): AuthPreferences =
        AuthPreferences(ctx)

    // Provides a singleton instance of AuthInterceptor using the AuthPreferences.
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        prefs: AuthPreferences
    ): AuthInterceptor =
        AuthInterceptor(prefs)

    // Adds the AuthInterceptor into a set of OkHttp interceptors.
    @Provides
    @IntoSet
    fun provideOkHttpInterceptor(
        authInterceptor: AuthInterceptor
    ): Interceptor = authInterceptor

    // Provides a singleton instance of AuthApiService created with Retrofit.
    @Provides
    @Singleton
    fun provideAuthApiService(
        retrofit: Retrofit
    ): AuthApiService =
        retrofit.create(AuthApiService::class.java)
}