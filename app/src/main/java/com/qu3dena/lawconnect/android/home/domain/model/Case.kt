package com.qu3dena.lawconnect.android.home.domain.model

import java.time.Instant
import java.util.UUID

data class Case(
    val id: UUID,
    val title: String,
    val description: String,
    val clientId: UUID,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant
)