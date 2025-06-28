package com.qu3dena.lawconnect.android.features.auth.data.di

import com.qu3dena.lawconnect.android.features.auth.data.repository.AuthRepositoryImpl
import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ) : AuthRepository
}