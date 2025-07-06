package com.qu3dena.lawconnect.android.features.cases.domain.repository

import kotlinx.coroutines.flow.Flow
import com.qu3dena.lawconnect.android.features.cases.domain.model.ApplicationToCase
import java.util.UUID

interface ApplicationRepository {
    fun submitApplication(caseId: UUID) : Flow<ApplicationToCase>
}