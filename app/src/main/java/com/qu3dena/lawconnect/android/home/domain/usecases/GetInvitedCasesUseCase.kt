package com.qu3dena.lawconnect.android.home.domain.usecases

import com.qu3dena.lawconnect.android.home.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.home.domain.repository.SummaryRepository
import kotlinx.coroutines.flow.Flow

class GetInvitedCasesUseCase(private val repository: SummaryRepository) {

    fun invoke(): Flow<List<InvitedCase>> = repository.getPostulatedCases()
}