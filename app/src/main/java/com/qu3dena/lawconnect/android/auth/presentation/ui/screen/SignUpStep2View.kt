package com.qu3dena.lawconnect.android.auth.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qu3dena.lawconnect.android.auth.data.source.local.SPECIALTY_MAP
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.SignUpUiState
import com.qu3dena.lawconnect.android.core.ui.components.CustomButton
import com.qu3dena.lawconnect.android.core.ui.components.CustomTextField
import com.qu3dena.lawconnect.android.core.ui.components.SelectableChipGroup

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
        Modifier
            .padding(16.dp),
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

        LazyRow {
            item {
                SelectableChipGroup(
                    items = SPECIALTY_MAP.values.toList(),
                    selectedItems = specialties,
                    onSelectionChanged = onSpecialtiesChange
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CustomButton(
                text = "Back",
                onClick = onBackClick,
                modifier = Modifier.weight(1f)
            )
            CustomButton(
                text = if (uiState == SignUpUiState.Loading) "Signing…" else "Sign Up",
                onClick = onSignUpClick,
                modifier = Modifier.weight(1f),
                enabled = uiState != SignUpUiState.Loading
            )
        }


        SnackbarHost(hostState = snackBarState)
    }
}
