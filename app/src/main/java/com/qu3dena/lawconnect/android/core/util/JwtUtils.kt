package com.qu3dena.lawconnect.android.core.util

import android.util.Base64
import org.json.JSONObject

object JwtUtils {
    fun extractUserId(token: String?): String? {
        if (token.isNullOrBlank()) return null
        val parts = token.split(".")
        if (parts.size < 2) return null

        return try {
            val payload = parts[1]
            val decoded = String(Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP))
            val json = JSONObject(decoded)
            val userId = json.optString("sub")
            if (userId.isBlank()) null else userId
        } catch (e: Exception) {
            null
        }
    }
}