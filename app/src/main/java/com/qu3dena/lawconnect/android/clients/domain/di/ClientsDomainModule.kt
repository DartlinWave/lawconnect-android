package com.qu3dena.lawconnect.android.clients.domain.di

import com.qu3dena.lawconnect.android.clients.domain.repository.ClientRepository
import com.qu3dena.lawconnect.android.clients.domain.usecases.GetClientsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientsDomainModule {

    @Provides
    @Singleton
    fun provideGetClientsIUseCase(repository: ClientRepository) =
        GetClientsUseCase(repository)

}