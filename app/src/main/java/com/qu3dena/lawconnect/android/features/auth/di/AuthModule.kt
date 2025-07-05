package com.qu3dena.lawconnect.android.features.auth.di

import android.content.Context
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.auth.data.remote.AuthApiService
import com.qu3dena.lawconnect.android.features.auth.data.remote.AuthInterceptor
import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import com.qu3dena.lawconnect.android.features.auth.presentation.navigation.AuthNavGraph

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthPreferences(
        @ApplicationContext ctx: Context
    ): AuthPreferences = AuthPreferences(ctx)

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        prefs: AuthPreferences
    ): AuthInterceptor = AuthInterceptor(prefs)

    @Provides
    @IntoSet
    fun provideOkHttpInterceptor(
        authInterceptor: AuthInterceptor
    ): Interceptor = authInterceptor

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService
    ): AuthRepository = authApiService

    @Provides
    @IntoSet
    fun provideAuthNavGraph(): FeatureNavGraph {
        return AuthNavGraph()
    }
}