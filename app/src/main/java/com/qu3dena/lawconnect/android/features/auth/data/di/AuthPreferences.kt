package com.qu3dena.lawconnect.android.features.auth.data.di

import android.content.Context
import com.qu3dena.lawconnect.android.core.util.JwtUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _token = MutableStateFlow(prefs.getString("auth_token", null))
    val token: StateFlow<String?> = _token

    private val _username = MutableStateFlow(prefs.getString("auth_username", null))
    val username: StateFlow<String?> = _username

    val userIdFlow: Flow<String?> = token.map { token ->
        JwtUtils.extractUserId(token)
    }

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        _token.value = token
    }

    fun saveUsername(username: String) {
        prefs.edit().putString("auth_username", username).apply()
        _username.value = username
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
        _token.value = null
    }

    fun clearUsername() {
        prefs.edit().remove("auth_username").apply()
        _username.value = null
    }
}