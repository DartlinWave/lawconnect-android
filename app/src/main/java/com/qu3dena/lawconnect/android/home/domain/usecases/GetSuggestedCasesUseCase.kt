package com.qu3dena.lawconnect.android.home.domain.usecases

import com.qu3dena.lawconnect.android.home.domain.model.Case
import com.qu3dena.lawconnect.android.home.domain.repository.SummaryRepository
import kotlinx.coroutines.flow.Flow

class GetSuggestedCasesUseCase(
    private val repository: SummaryRepository
) {
    fun invoke(): Flow<List<Case>> = repository.getSuggestedCases()
}