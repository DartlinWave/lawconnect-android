package com.qu3dena.lawconnect.android.clients.data.di

import com.qu3dena.lawconnect.android.clients.data.source.remote.ClientApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientNetworkModule {

    @Provides
    @Singleton
    fun provideClientApiService(
        retrofit: Retrofit
    ): ClientApiService =
        retrofit.create(ClientApiService::class.java)
}