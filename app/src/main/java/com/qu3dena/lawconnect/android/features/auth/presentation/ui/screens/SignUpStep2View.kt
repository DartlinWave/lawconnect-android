package com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.qu3dena.lawconnect.android.features.auth.data.entities.SPECIALTY_MAP
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels.SignUpUiState
import com.qu3dena.lawconnect.android.shared.components.BrownActionButton
import com.qu3dena.lawconnect.android.shared.components.CustomTextField
import com.qu3dena.lawconnect.android.shared.components.DarkBrownActionButton
import com.qu3dena.lawconnect.android.shared.components.SelectableChipGroup
import android.widget.Toast
import android.util.Log

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
    dni: String = "",
    onDniChange: (String) -> Unit = {},
    phoneNumber: String = "",
    onPhoneNumberChange: (String) -> Unit = {},
    address: String = "",
    onAddressChange: (String) -> Unit = {},
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    onShowError: (String) -> Unit,
    onSuccess: () -> Unit
) {
    val snackBarState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignUpUiState.Error -> {
                Log.e("SignUpStep2View", "Error state: ${uiState.message}")
                onShowError(uiState.message)
            }
            is SignUpUiState.Success -> {
                Log.d("SignUpStep2View", "Success state reached")
                // Agregar un mensaje de depuración cuando el registro sea exitoso
                Toast.makeText(context, "Registration successful! Redirecting to login...", Toast.LENGTH_SHORT).show()
                try {
                    onSuccess()
                } catch (e: Exception) {
                    Log.e("SignUpStep2View", "Error during success callback", e)
                    onShowError("Navigation error: ${e.message}")
                }
            }
            else -> {}
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Logo
        item {
            AsyncImage(
                model = "https://raw.githubusercontent.com/DartlinWave/lawconnect-report/refs/heads/main/assets/images/chapter-V/LogoLawConnect.png",
                contentDescription = "LawConnect Logo",
                modifier = Modifier.scale(0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Formulario de datos personales
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    value = dni,
                    onValueChange = onDniChange,
                    placeholder = "DNI/ID Number"
                )
                CustomTextField(
                    value = phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    placeholder = "Phone Number"
                )
                CustomTextField(
                    value = address,
                    onValueChange = onAddressChange,
                    placeholder = "Address"
                )
                CustomTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    placeholder = "Professional description"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Especialidades
        item {
            SpecialtyGallery(
                specialties = specialties,
                onSpecialtiesChange = onSpecialtiesChange
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botones
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BrownActionButton(
                    text = "Back",
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                )
                DarkBrownActionButton(
                    text = if (uiState == SignUpUiState.Loading) "Signing…" else "Sign Up",
                    onClick = onSignUpClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState != SignUpUiState.Loading
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SnackbarHost(hostState = snackBarState)
        }
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