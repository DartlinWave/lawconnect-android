package com.qu3dena.lawconnect.android.features.cases.data.remote

import android.util.Log
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

import com.qu3dena.lawconnect.android.shared.services.ApiService
import com.qu3dena.lawconnect.android.features.cases.data.entities.toDomain
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.cases.domain.model.ApplicationToCase
import com.qu3dena.lawconnect.android.features.cases.domain.repository.ApplicationRepository
import com.qu3dena.lawconnect.android.features.cases.data.entities.ApplicationToCaseRequestDto
import com.qu3dena.lawconnect.android.features.cases.data.entities.ApplicationToCaseResponseDto
import java.util.UUID

class ApplicationApiService @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) : ApplicationRepository {

    override fun submitApplication(caseId: UUID): Flow<ApplicationToCase> = flow {
        try {
            val userId = authPreferences.userIdFlow.first()

            if (userId == null) {
                throw IllegalStateException("User ID is not available")
            }

            val request = ApplicationToCaseRequestDto(
                caseId = caseId,
                lawyerId = UUID.fromString(userId)
            )

            val response = apiService
                .post<
                        ApplicationToCaseRequestDto, ApplicationToCaseResponseDto>(
                    "applications", request
                )

            Log.d("ApplicationApiService", "Response of submitApplication: $response")

            emit(response.toDomain())
        } catch (_: Exception) {
            throw Exception("Failed to submit application")
        }
    }.flowOn(Dispatchers.IO)
}