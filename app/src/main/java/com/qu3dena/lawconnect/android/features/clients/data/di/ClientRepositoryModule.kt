package com.qu3dena.lawconnect.android.features.clients.data.di

import com.qu3dena.lawconnect.android.features.clients.data.repository.ClientRepositoryImpl
import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSummaryRepository(
        impl: ClientRepositoryImpl
    ): ClientRepository
}