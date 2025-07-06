package com.qu3dena.lawconnect.android.features.auth.domain.model

data class SignInResponse(
    val id: String,
    val username: String,
    val token: String
)