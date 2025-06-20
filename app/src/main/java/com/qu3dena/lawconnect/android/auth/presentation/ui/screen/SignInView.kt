package com.qu3dena.lawconnect.android.auth.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.SignInUiState
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.SignInViewModel
import com.qu3dena.lawconnect.android.core.ui.components.BrownActionButton
import com.qu3dena.lawconnect.android.core.ui.components.CustomTextField
import com.qu3dena.lawconnect.android.core.ui.components.DarkBrownActionButton

@Composable
fun SignInView(
    viewModel: SignInViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState) {
        if (uiState is SignInUiState.Success) {
            viewModel.resetState()
            onSuccess()
        }
    }

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
                value = viewModel.username,
                onValueChange = { viewModel.username = it },
                placeholder = "Username"
            )

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                placeholder = "Password"
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        if (uiState is SignInUiState.Error) {
            Text(
                text = (uiState as SignInUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "Forgot Password?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Sign In
            BrownActionButton(
                text = if (uiState == SignInUiState.Loading) "Signing In…" else "Sign In",
                onClick = { viewModel.signIn(onSuccess) },
                enabled = uiState != SignInUiState.Loading
            )

            // Sign Up
            DarkBrownActionButton(
                text = "Sign Up",
                onClick = onSignUpClick,
                enabled = uiState != SignInUiState.Loading
            )
        }
    }

}
