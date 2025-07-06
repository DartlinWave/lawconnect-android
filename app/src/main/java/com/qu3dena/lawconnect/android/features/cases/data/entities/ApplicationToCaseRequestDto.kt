package com.qu3dena.lawconnect.android.features.cases.data.entities

import java.util.UUID

data class ApplicationToCaseRequestDto(
    val caseId: UUID,
    val lawyerId: UUID,
)