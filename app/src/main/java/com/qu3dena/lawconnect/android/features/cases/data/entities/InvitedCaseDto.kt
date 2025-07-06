package com.qu3dena.lawconnect.android.features.cases.data.entities

import java.util.UUID
import com.qu3dena.lawconnect.android.features.cases.domain.model.InvitedCase

data class InvitedCaseDto(
    val id: Long,
    val caseId: UUID,
    val lawyerId: UUID,
    val status: String
)

fun InvitedCaseDto.toDomain(title: String): InvitedCase = InvitedCase(
    id = id,
    title = title,
    caseId = caseId,
    lawyerId = lawyerId,
    status = status
)