package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileRequest
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileResponse
import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateLawyerProfileUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(lawyerProfileRequest: LawyerProfileRequest): Flow<LawyerProfileResponse> =
        repository.createLawyerProfile(lawyerProfileRequest)
}
