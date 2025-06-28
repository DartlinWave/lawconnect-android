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

    private val _tokenFlow = MutableStateFlow(prefs.getString("auth_token", null))
    val tokenFlow: StateFlow<String?> = _tokenFlow

    private val _usernameFlow = MutableStateFlow(prefs.getString("auth_username", null))
    val usernameFlow: StateFlow<String?> = _usernameFlow

    val userIdFlow: Flow<String?> = tokenFlow.map { token ->
        JwtUtils.extractUserId(token)
    }

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        _tokenFlow.value = token
    }

    fun saveUsername(username: String) {
        prefs.edit().putString("auth_username", username).apply()
        _usernameFlow.value = username
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
        _tokenFlow.value = null
    }

    fun clearUsername() {
        prefs.edit().remove("auth_username").apply()
        _usernameFlow.value = null
    }
}