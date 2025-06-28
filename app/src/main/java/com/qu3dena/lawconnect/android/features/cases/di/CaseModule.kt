package com.qu3dena.lawconnect.android.features.cases.di

import com.qu3dena.lawconnect.android.features.cases.data.remote.CaseApiService
import com.qu3dena.lawconnect.android.features.cases.data.remote.LookupService
import com.qu3dena.lawconnect.android.features.cases.data.repository.CaseRepositoryImpl
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository
import com.qu3dena.lawconnect.android.shared.contracts.CaseLookupService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CaseModule {

    @Provides
    @Singleton
    fun provideCaseRepository(
        caseApiService: CaseRepositoryImpl
    ): CaseRepository = caseApiService

    @Provides
    @Singleton
    fun provideCaseApiService(
        retrofit: Retrofit
    ): CaseApiService =
        retrofit.create(CaseApiService::class.java)

    @Provides
    @Singleton
    fun provideCaseLookupService(
        lookupService: LookupService
    ): CaseLookupService = lookupService
}