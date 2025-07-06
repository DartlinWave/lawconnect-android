package com.qu3dena.lawconnect.android.core.di

import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object LoggingInterceptorModule {
    @Provides
    @Singleton
    @IntoSet
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}