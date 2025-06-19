package com.qu3dena.lawconnect.android.auth.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SignInView(
    onSignUpClick: () -> Unit
) {
    Column {

        Text("Bienvenido, haz SignIn")
        TextButton(onClick = onSignUpClick) {
            Text("Sign Up")
        }
    }
}
