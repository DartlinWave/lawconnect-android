package com.qu3dena.lawconnect.android.features.auth.data.entities

import com.qu3dena.lawconnect.android.features.auth.domain.model.SignUpResponse

data class SignUpResponseDto(
    val id: String,
    val username: String,
    val role: String
) {
    fun toSignUpResponse(): SignUpResponse {
        return SignUpResponse(
            id = id,
            username = username,
            role = role
        )
    }
}
