package com.qu3dena.lawconnect.android.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

import com.qu3dena.lawconnect.android.shared.contracts.AuthSessionManager
import com.qu3dena.lawconnect.android.features.auth.data.AuthSessionService
/**
 * Hilt module that provides bindings for authentication session management.
 * 
 * This module follows Clean Architecture principles by:
 * - Binding concrete implementations to contracts
 * - Keeping the core module independent of specific feature implementations
 * - Allowing dependency inversion through contracts
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthSessionModule {
    
    /**
     * Binds AuthSessionService to AuthSessionManager contract.
     * This allows the core to use the contract without knowing the concrete implementation.
     */
    @Binds
    @Singleton
    abstract fun bindAuthSessionManager(
        authSessionService: AuthSessionService
    ): AuthSessionManager
} 