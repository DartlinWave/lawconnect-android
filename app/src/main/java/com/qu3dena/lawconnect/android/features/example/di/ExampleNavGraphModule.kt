package com.qu3dena.lawconnect.android.features.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent

import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.example.presentation.navigation.ExampleNavGraph

/**
 * Example feature navigation graph module.
 * 
 * This module demonstrates how to add a new feature to the navigation system
 * following Clean Architecture principles:
 * 
 * 1. Create a feature module
 * 2. Implement FeatureNavGraph interface
 * 3. Create a DI module with @IntoSet annotation
 * 4. That's it! The feature will be automatically included
 * 
 * No need to modify core code, no registration, no reflection.
 */
@Module
@InstallIn(SingletonComponent::class)
object ExampleNavGraphModule {

    @Provides
    @IntoSet
    fun provideExampleNavGraph(): FeatureNavGraph {
        return ExampleNavGraph()
    }
} 