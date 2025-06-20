package com.qu3dena.lawconnect.android.home.data.model

import com.qu3dena.lawconnect.android.home.domain.model.InvitedCase
import java.util.UUID

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