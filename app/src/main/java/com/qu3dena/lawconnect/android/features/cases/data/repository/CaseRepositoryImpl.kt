package com.qu3dena.lawconnect.android.features.cases.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.cases.data.entities.toDomain
import com.qu3dena.lawconnect.android.features.cases.data.remote.CaseApiService
import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CaseRepositoryImpl @Inject constructor(
    private val api: CaseApiService,
    private val authPreferences: AuthPreferences
) : CaseRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSuggestedCases(): Flow<List<Case>> = flow {
        val userId = authPreferences.userIdFlow.first()
            ?: throw IllegalStateException("The user ID is not available. Please log in first.")

        val suggestedCases = api.getSuggestedCases(lawyerId = userId)
            .map { it.toDomain() }

        emit(suggestedCases)
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPostulatedCases(): Flow<List<InvitedCase>> = flow {
        val userId = authPreferences.userIdFlow.first()
            ?: throw IllegalStateException("The user ID is not available. Please log in first.")

        val invitedCases = api.getInvitedCases(lawyerId = userId)
            .map {
                api.getCaseById(it.caseId.toString()).toDomain().let { case ->
                    it.toDomain(case.title)
                }
            }

        emit(invitedCases)
    }.flowOn(Dispatchers.IO)

}