package com.qu3dena.lawconnect.android.features.clients.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.clients.presentation.navigation.ClientNavGraph

/**
 * Client feature navigation graph module.
 * 
 * This module provides the ClientNavGraph implementation of the FeatureNavGraph interface
 * using @IntoSet annotation for automatic collection by Hilt.
 * 
 * Clean Architecture principles:
 * - Feature provides its own implementation of the core interface
 * - Core doesn't know about this specific feature
 * - Dependency inversion is maintained
 */
@Module
@InstallIn(SingletonComponent::class)
object ClientNavGraphModule {

    @Provides
    @IntoSet
    fun provideClientNavGraph(): FeatureNavGraph {
        return ClientNavGraph()
    }
} 