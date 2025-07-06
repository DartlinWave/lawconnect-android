package com.qu3dena.lawconnect.android.features.cases.data.entities

import com.qu3dena.lawconnect.android.features.cases.domain.model.ApplicationToCase
import java.util.UUID

data class ApplicationToCaseResponseDto(
    val id: Long,
    val caseId: UUID,
    val lawyerId: UUID,
    val status: String,
)

fun ApplicationToCaseResponseDto.toDomain(): ApplicationToCase {
    return ApplicationToCase(
        id = id,
        caseId = caseId,
        lawyerId = lawyerId,
        status = status
    )
}