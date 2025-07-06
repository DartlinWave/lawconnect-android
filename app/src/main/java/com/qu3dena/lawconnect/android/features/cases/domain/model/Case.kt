package com.qu3dena.lawconnect.android.features.cases.domain.model

import java.util.UUID
import java.time.Instant

data class Case(
    val id: UUID,
    val title: String,
    val description: String,
    val clientId: UUID,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant
)