package com.qu3dena.lawconnect.android.features.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.auth.presentation.navigation.AuthNavGraph

/**
 * Auth feature navigation graph module.
 *
 * This module provides the AuthNavGraph implementation of the FeatureNavGraph interface
 * using @IntoSet annotation, which allows Hilt to automatically collect all
 * FeatureNavGraph implementations into a Set.
 *
 * Clean Architecture principles:
 * - Feature provides its own implementation of the core interface
 * - Core doesn't know about this specific feature
 * - Dependency inversion is maintained
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthNavGraphModule {

    @Provides
    @IntoSet
    fun provideAuthNavGraph(): FeatureNavGraph {
        return AuthNavGraph()
    }
}