package com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.shared.components.BrownActionButton
import com.qu3dena.lawconnect.android.shared.components.CustomTextField

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://raw.githubusercontent.com/DartlinWave/lawconnect-report/refs/heads/main/assets/images/chapter-V/LogoLawConnect.png",
            contentDescription = "LawConnect Logo",
            modifier = Modifier.scale(0.75f)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        BrownActionButton(
            text           = "Next",
            onClick        = onNext,
            contentColor   = Color.White,
            modifier       = Modifier.fillMaxWidth(),
            enabled        = enabled
        )
    }
}
