package com.qu3dena.lawconnect.android.features.cases.domain.repository

import kotlinx.coroutines.flow.Flow
import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.model.InvitedCase

interface CaseRepository {
    fun getAcceptedCases(): Flow<List<Case>>

    fun getSuggestedCases(): Flow<List<Case>>

    fun getCaseById(caseId: String): Flow<Case>

    fun getPostulatedCases(): Flow<List<InvitedCase>>
}