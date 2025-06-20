package com.qu3dena.lawconnect.android.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.home.data.model.toDomain
import com.qu3dena.lawconnect.android.home.data.source.remote.SummaryApiService
import com.qu3dena.lawconnect.android.home.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.home.domain.model.Case
import com.qu3dena.lawconnect.android.home.domain.repository.SummaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SummaryRepositoryImpl @Inject constructor(
    private val api: SummaryApiService,
    private val authPreferences: AuthPreferences
) : SummaryRepository {

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