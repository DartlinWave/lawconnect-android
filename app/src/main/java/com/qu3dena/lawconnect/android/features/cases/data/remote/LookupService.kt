package com.qu3dena.lawconnect.android.features.cases.data.remote

import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository
import com.qu3dena.lawconnect.android.shared.contracts.CaseLookupService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LookupService @Inject constructor(
    private val repository: CaseRepository
) : CaseLookupService {

    override suspend fun getCaseTitles(): List<String> {
        return repository.getSuggestedCases().first().map { it.title }
    }

    override suspend fun getCaseStatuses(): List<String> {
        return repository.getSuggestedCases().first().map { it.status }
    }

    override suspend fun getInvitedCaseTitles(): List<String> {
        return repository.getPostulatedCases().first().map { it.title }
    }

    override suspend fun getInvitedCaseStatuses(): List<String> {
        return repository.getPostulatedCases().first().map { it.status }
    }
}