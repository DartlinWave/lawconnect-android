package com.qu3dena.lawconnect.android.features.cases.domain.usecases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository

class GetCaseClientsUseCase @Inject constructor(
    private val caseRepository: CaseRepository
) {
    operator fun invoke(): Flow<List<Case>> = caseRepository.getAcceptedCases()
}