package com.qu3dena.lawconnect.android.features.home.di

import com.qu3dena.lawconnect.android.features.home.presentation.navigation.HomeNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object HomeNavGraphModule {

    @Provides
    @IntoSet
    fun provideHomeNavGraph(): FeatureNavGraph {
        return HomeNavGraph()
    }
}