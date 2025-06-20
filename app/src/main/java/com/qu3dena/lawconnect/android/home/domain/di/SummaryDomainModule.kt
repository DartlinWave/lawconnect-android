package com.qu3dena.lawconnect.android.home.domain.di

import com.qu3dena.lawconnect.android.home.domain.repository.SummaryRepository
import com.qu3dena.lawconnect.android.home.domain.usecases.GetInvitedCasesUseCase
import com.qu3dena.lawconnect.android.home.domain.usecases.GetSuggestedCasesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SummaryDomainModule {

    @Provides
    @Singleton
    fun provideGetSuggestedCasesUseCase(repository: SummaryRepository) =
        GetSuggestedCasesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetInvitedCasesUseCase(repository: SummaryRepository) =
        GetInvitedCasesUseCase(repository)
}