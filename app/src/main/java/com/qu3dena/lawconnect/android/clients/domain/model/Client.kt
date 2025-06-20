package com.qu3dena.lawconnect.android.clients.domain.model

import java.time.Instant
import java.util.UUID

data class Client(
    val id: UUID,
    val title: String,
    val description: String,
    val clientId: UUID,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant
)