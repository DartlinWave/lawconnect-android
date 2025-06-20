package com.qu3dena.lawconnect.android.clients.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.clients.domain.model.Client
import java.time.Instant
import java.util.UUID

data class ClientDto(
    val id: UUID,
    val title: String,
    val description: String,
    val clientId: UUID,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun ClientDto.toDomain(): Client =
    Client(
        id = id,
        title = title,
        description = description,
        clientId = clientId,
        status = status,
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt),
    )