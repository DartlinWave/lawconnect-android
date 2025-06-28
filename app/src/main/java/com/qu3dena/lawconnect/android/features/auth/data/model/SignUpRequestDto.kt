package com.qu3dena.lawconnect.android.features.auth.data.model

data class SignUpRequestDto(
    val username: String,
    val password: String,
    val role: String
)