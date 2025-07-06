package com.qu3dena.lawconnect.android.features.cases.domain.model

import java.util.UUID

data class InvitedCase(
    val id: Long,
    val title: String,
    val caseId: UUID,
    val lawyerId: UUID,
    val status: String
)