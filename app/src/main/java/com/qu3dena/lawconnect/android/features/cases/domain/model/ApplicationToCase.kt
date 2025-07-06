package com.qu3dena.lawconnect.android.features.cases.domain.model

import java.util.UUID

data class ApplicationToCase(
    val id: Long,
    val caseId: UUID,
    val lawyerId: UUID,
    val status: String
)