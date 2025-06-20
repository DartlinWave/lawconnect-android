package com.qu3dena.lawconnect.android.home.data.di

import com.qu3dena.lawconnect.android.home.data.repository.SummaryRepositoryImpl
import com.qu3dena.lawconnect.android.home.domain.repository.SummaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SummaryRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSummaryRepository(
        impl: SummaryRepositoryImpl
    ): SummaryRepository
}