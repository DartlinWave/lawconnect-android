package com.qu3dena.lawconnect.android.auth.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.core.ui.components.BrownActionButton
import com.qu3dena.lawconnect.android.core.ui.components.CustomTextField
import com.qu3dena.lawconnect.android.core.ui.components.DarkBrownActionButton

@Composable
fun SignInView(
    onSignUpClick: () -> Unit,
    onSignInClick: (String, String) -> Unit
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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
                modifier = Modifier.fillMaxWidth(),
                value = username.value,
                onValueChange = { username.value = it },
                placeholder = "Username"
            )

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = "Password"
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Forgot Password?",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BrownActionButton(
                text = "Sign In",
                onClick = { onSignInClick(username.value, password.value) }
            )


            DarkBrownActionButton(
                text = "Sign Up",
                onClick = onSignUpClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInViewPreview() {
    SignInView(
        onSignUpClick = {},
        onSignInClick = { _, _ -> }
    )
}