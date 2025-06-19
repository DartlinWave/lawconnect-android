package com.qu3dena.lawconnect.android.auth.domain.model

data class SignInResponse(
    val id: String,
    val username: String,
    val token: String
)