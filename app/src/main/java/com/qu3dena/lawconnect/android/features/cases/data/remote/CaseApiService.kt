package com.qu3dena.lawconnect.android.features.cases.data.remote

import android.os.Build
import androidx.annotation.RequiresApi

import com.qu3dena.lawconnect.android.shared.services.ApiService
import com.qu3dena.lawconnect.android.features.cases.domain.model.Case
import com.qu3dena.lawconnect.android.features.cases.data.entities.CaseDto
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.cases.data.entities.toDomain
import com.qu3dena.lawconnect.android.features.cases.domain.model.InvitedCase
import com.qu3dena.lawconnect.android.features.cases.data.entities.InvitedCaseDto
import com.qu3dena.lawconnect.android.features.cases.domain.repository.CaseRepository

import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class CaseApiService @Inject constructor(
    private val api: ApiService,
    private val authPreferences: AuthPreferences
) : CaseRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAcceptedCases(): Flow<List<Case>> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()

            if (userId == null) {
                emit(emptyList())
                return@flow
            }

            val acceptedCases = api.get<List<CaseDto>>("cases/lawyer/$userId")
                .map { it.toDomain() }

            emit(acceptedCases)
        }
        catch (_: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSuggestedCases(): Flow<List<Case>> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()

            if (userId == null) {
                emit(emptyList())
                return@flow
            }

            val cases = api.get<List<CaseDto>>("cases/suggested?lawyerId=$userId")
                .map { it.toDomain() }

            emit(cases)
        } catch (_: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPostulatedCases(): Flow<List<InvitedCase>> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()
            if (userId == null) {
                emit(emptyList())
                return@flow
            }

            val invitedCases = api.get<List<InvitedCaseDto>>("invitations?lawyerId=$userId")
                .map {
                    val case = getCaseById(it.caseId.toString()).first()
                    it.toDomain(case.title)
                }

            emit(invitedCases)
        } catch (_: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCaseById(caseId: String): Flow<Case> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()

            if (userId == null) {
                throw IllegalStateException("User not authenticated")
            }

            val case = api.get<CaseDto>("cases/$caseId")

            emit(case.toDomain())
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(Dispatchers.IO)
}