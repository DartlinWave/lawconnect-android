package com.qu3dena.lawconnect.android.features.auth.data.model

import com.qu3dena.lawconnect.android.features.auth.domain.model.SignInResponse

data class SignInResponseDto(
    val id: String,
    val username: String,
    val token: String
) {
    fun toSignInResponse(): SignInResponse {
        return SignInResponse(
            id = id,
            username = username,
            token = token
        )
    }
}