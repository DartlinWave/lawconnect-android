package com.qu3dena.lawconnect.android.auth.domain.di

import com.qu3dena.lawconnect.android.auth.domain.repository.AuthRepository
import com.qu3dena.lawconnect.android.auth.domain.usecases.GetSessionStateUseCase
import com.qu3dena.lawconnect.android.auth.domain.usecases.SignInUseCase
import com.qu3dena.lawconnect.android.auth.domain.usecases.SignOutUseCase
import com.qu3dena.lawconnect.android.auth.domain.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository) =
        SignInUseCase(repository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository) =
        SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideSignOutUseCase(repository: AuthRepository) =
        SignOutUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSessionStateUseCase(repository: AuthRepository) =
        GetSessionStateUseCase(repository)
}