package com.qu3dena.lawconnect.android.features.cases.di

import com.qu3dena.lawconnect.android.features.cases.data.remote.CaseApiService
import com.qu3dena.lawconnect.android.features.cases.data.remote.LookupService
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository
import com.qu3dena.lawconnect.android.features.cases.presentation.navigation.CaseNavGraph
import com.qu3dena.lawconnect.android.shared.contracts.CaseLookupService
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CaseModule {

    @Provides
    @Singleton
    fun provideCaseRepository(
        caseApiService: CaseApiService
    ): CaseRepository = caseApiService

    @Provides
    @Singleton
    fun provideCaseLookupService(
        lookupService: LookupService
    ): CaseLookupService = lookupService

    @Provides
    @IntoSet
    fun provideCaseNavGraph(): FeatureNavGraph {
        return CaseNavGraph()
    }
}