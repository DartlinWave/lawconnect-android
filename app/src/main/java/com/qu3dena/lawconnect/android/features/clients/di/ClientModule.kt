package com.qu3dena.lawconnect.android.features.clients.di

import com.qu3dena.lawconnect.android.features.clients.data.remote.ClientApiService
import com.qu3dena.lawconnect.android.features.clients.domain.repository.ClientRepository
import com.qu3dena.lawconnect.android.features.clients.presentation.navigation.ClientNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    fun provideClientRepository(
        clientApiService: ClientApiService
    ): ClientRepository = clientApiService

    @Provides
    @IntoSet
    fun provideClientNavGraph(): FeatureNavGraph {
        return ClientNavGraph()
    }
}
