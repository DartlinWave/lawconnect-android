package com.qu3dena.lawconnect.android.home.data.di

import com.qu3dena.lawconnect.android.home.data.source.remote.SummaryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SummaryNetworkModule {

    @Provides
    @Singleton
    fun provideSummaryApiService(
        retrofit: Retrofit
    ): SummaryApiService =
        retrofit.create(SummaryApiService::class.java)
}