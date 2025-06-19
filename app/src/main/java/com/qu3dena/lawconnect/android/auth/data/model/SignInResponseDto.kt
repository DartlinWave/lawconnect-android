package com.qu3dena.lawconnect.android.auth.data.model

import com.qu3dena.lawconnect.android.auth.domain.model.SignInResponse

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