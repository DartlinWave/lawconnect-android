package com.qu3dena.lawconnect.android.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

/**
 * Core navigation graph module.
 * 
 * This module collects all FeatureNavGraph implementations provided by feature modules
 * through dependency injection using Hilt's @IntoSet annotation.
 * 
 * Clean Architecture principles:
 * - Core defines the interface (FeatureNavGraph)
 * - Features provide implementations through their own DI modules
 * - Core doesn't know about specific features
 * - Dependency inversion is maintained
 */
@Module
@InstallIn(SingletonComponent::class)
object NavGraphModule {

    @Provides
    @Singleton
    fun provideFeatureNavGraphs(
        navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>
    ): List<@JvmSuppressWildcards FeatureNavGraph> {
        return navGraphs.toList()
    }
}