package com.qu3dena.lawconnect.android.features.profile.di

import com.qu3dena.lawconnect.android.features.profile.presentation.navigation.ProfileNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @IntoSet
    fun provideProfileNavGraph(): FeatureNavGraph {
        return ProfileNavGraph()
    }
}
