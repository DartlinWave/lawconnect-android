package com.qu3dena.lawconnect.android.features.cases.domain.repository

import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.model.InvitedCase
import kotlinx.coroutines.flow.Flow

interface CaseRepository {
    fun getSuggestedCases(): Flow<List<Case>>

    fun getPostulatedCases(): Flow<List<InvitedCase>>
}