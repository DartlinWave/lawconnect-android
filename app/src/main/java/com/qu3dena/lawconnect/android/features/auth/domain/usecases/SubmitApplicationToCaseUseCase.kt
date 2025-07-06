package com.qu3dena.lawconnect.android.features.auth.domain.usecases

import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.qu3dena.lawconnect.android.features.cases.domain.model.ApplicationToCase
import com.qu3dena.lawconnect.android.features.cases.domain.repository.ApplicationRepository

class SubmitApplicationToCaseUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {
    operator fun invoke(caseId: UUID): Flow<ApplicationToCase> =
        applicationRepository.submitApplication(caseId)
}