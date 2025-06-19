package com.qu3dena.lawconnect.android.auth.data.di

import com.qu3dena.lawconnect.android.auth.data.repository.AuthRepositoryImpl
import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository
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