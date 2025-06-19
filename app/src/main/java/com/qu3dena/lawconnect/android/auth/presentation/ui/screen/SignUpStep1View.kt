package com.qu3dena.lawconnect.android.auth.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qu3dena.lawconnect.android.core.ui.components.CustomButton
import com.qu3dena.lawconnect.android.core.ui.components.CustomTextField

@Composable
fun SignUpStep1View(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPass: String,
    onConfirmPassChange: (String) -> Unit,
    onNext: () -> Unit
) {
    val enabled = password.isNotBlank() && password == confirmPass

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CustomTextField(
            value = username,
            onValueChange = onUsernameChange,
            placeholder = "Username"
        )
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Password"
        )
        CustomTextField(
            value = confirmPass,
            onValueChange = onConfirmPassChange,
            placeholder = "Confirm Password"
        )
        CustomButton(
            text           = "Next",
            onClick        = onNext,
            backgroundColor= if (enabled) Color(0xFF6200EE) else Color.Gray,
            contentColor   = Color.White,
            modifier       = Modifier.fillMaxWidth(),
            enabled        = enabled
        )
    }
}
