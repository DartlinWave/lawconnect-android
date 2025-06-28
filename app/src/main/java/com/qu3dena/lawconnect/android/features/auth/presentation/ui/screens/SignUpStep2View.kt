package com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.features.auth.data.source.local.SPECIALTY_MAP
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels.SignUpUiState
import com.qu3dena.lawconnect.android.shared.components.BrownActionButton
import com.qu3dena.lawconnect.android.shared.components.CustomTextField
import com.qu3dena.lawconnect.android.shared.components.DarkBrownActionButton
import com.qu3dena.lawconnect.android.shared.components.SelectableChipGroup

@Composable
fun SignUpStep2View(
    uiState: SignUpUiState,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    specialties: List<String>,
    onSpecialtiesChange: (List<String>) -> Unit,
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    onShowError: (String) -> Unit,
    onSuccess: () -> Unit
) {
    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        if (uiState is SignUpUiState.Error) {
            onShowError(uiState.message)
        }
        if (uiState is SignUpUiState.Success) {
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
                value = firstName,
                onValueChange = onFirstNameChange,
                placeholder = "First Name"
            )
            CustomTextField(
                value = lastName,
                onValueChange = onLastNameChange,
                placeholder = "Last Name"
            )
            CustomTextField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholder = "Professional description"
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        SpecialtyGallery(
            specialties = specialties,
            onSpecialtiesChange = onSpecialtiesChange
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Column (verticalArrangement = Arrangement.spacedBy(8.dp)) {
            BrownActionButton(
                text = "Back",
                onClick = onBackClick,
                modifier = Modifier.weight(1f)
            )
            DarkBrownActionButton(
                text = if (uiState == SignUpUiState.Loading) "Signing…" else "Sign Up",
                onClick = onSignUpClick,
                modifier = Modifier.weight(1f),
                enabled = uiState != SignUpUiState.Loading
            )
        }

        SnackbarHost(hostState = snackBarState)
    }
}

@Composable
fun SpecialtyGallery(
    specialties: List<String>,
    onSpecialtiesChange: (List<String>) -> Unit
) {
    LazyRow {
        item {
            SelectableChipGroup(
                items = SPECIALTY_MAP.values.toList(),
                selectedItems = specialties,
                onSelectionChanged = onSpecialtiesChange
            )
        }
    }
}