package com.qu3dena.lawconnect.android.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        OkHttpClient.Builder()
            .also { builder ->
                interceptors.forEach { builder.addInterceptor(it) }
            }
            .build()
}