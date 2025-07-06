package com.qu3dena.lawconnect.android.features.cases.data.entities

import java.util.UUID
import java.time.Instant
import android.os.Build
import androidx.annotation.RequiresApi
import com.qu3dena.lawconnect.android.features.cases.domain.model.Case

data class CaseDto(
    val id: UUID,
    val title: String,
    val description: String,
    val clientId: UUID,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun CaseDto.toDomain(): Case =
    Case(
        id = id,
        title = title,
        description = description,
        clientId = clientId,
        status = status,
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt),
    )