package com.qu3dena.lawconnect.android.auth.data.model

import com.qu3dena.lawconnect.android.auth.domain.model.SignUpResponse

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