package com.qu3dena.lawconnect.android.auth.data.model

data class SignUpRequestDto(
    val username: String,
    val password: String,
    val role: String
)