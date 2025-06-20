package com.qu3dena.lawconnect.android.home.domain.repository

import com.qu3dena.lawconnect.android.home.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.home.domain.model.Case
import kotlinx.coroutines.flow.Flow

interface SummaryRepository {
    fun getSuggestedCases(): Flow<List<Case>>

    fun getPostulatedCases(): Flow<List<InvitedCase>>
}