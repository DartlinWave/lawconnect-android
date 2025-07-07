package com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qu3dena.lawconnect.android.features.auth.domain.model.ContactInfo
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileRequest
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.CreateLawyerProfileUseCase
import com.qu3dena.lawconnect.android.features.auth.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val createLawyerProfileUseCase: CreateLawyerProfileUseCase
) : ViewModel() {

    // Paso 1
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPass by mutableStateOf("")

    // Paso 2
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var description by mutableStateOf("")
    var specialties by mutableStateOf<List<String>>(emptyList())

    // Additional lawyer profile fields
    var dni by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var address by mutableStateOf("")

    private val _uiState = mutableStateOf<SignUpUiState>(SignUpUiState.Idle)
    val uiState: State<SignUpUiState> = _uiState

    fun signUp() {
        viewModelScope.launch {
            try {
                Log.d("SignUpViewModel", "Starting signup process for user: $username")
                signUpUseCase.invoke(username, password)
                    .onStart {
                        Log.d("SignUpViewModel", "Signup started")
                        _uiState.value = SignUpUiState.Loading
                    }
                    .catch { e ->
                        Log.e("SignUpViewModel", "Error during signup", e)
                        handleError(e)
                    }
                    .collect { signUpResponse ->
                        Log.d("SignUpViewModel", "Signup successful, user ID: ${signUpResponse.id}")
                        // After successful signup, create the lawyer profile
                        createLawyerProfile(signUpResponse.id)
                    }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Unexpected error during signup", e)
                handleError(e)
            }
        }
    }

    private fun createLawyerProfile(userId: String) {
        viewModelScope.launch {
            try {
                // Log para verificar el userId que estamos recibiendo
                println("DEBUG: Creating lawyer profile for userId: $userId")

                val formattedSpecialties = specialties.map { specialty ->
                    mapDisplayNameToEnumValue(specialty)
                }

                val lawyerProfileRequest = LawyerProfileRequest(
                    userId = userId,
                    firstname = firstName,
                    lastname = lastName,
                    dni = dni,
                    contactInfo = ContactInfo(
                        phoneNumber = phoneNumber,
                        address = address
                    ),
                    description = description,
                    specialties = formattedSpecialties
                )

                // Log para verificar el request completo
                println("DEBUG: LawyerProfileRequest - userId: ${lawyerProfileRequest.userId}, firstname: ${lawyerProfileRequest.firstname}, lastname: ${lawyerProfileRequest.lastname}")

                createLawyerProfileUseCase.invoke(lawyerProfileRequest)
                    .collect {
                        // Log para verificar la respuesta
                        println("DEBUG: Lawyer profile created successfully: $it")
                        _uiState.value = SignUpUiState.Success
                    }
            } catch (e: Exception) {
                println("DEBUG: Error creating lawyer profile: ${e.message}")
                handleError(e)
            }
        }
    }

    /**
     * Maps display names of specialties to their corresponding enum values in the backend.
     * This ensures compatibility with the LawyerSpecialties enum in the backend.
     */
    private fun mapDisplayNameToEnumValue(displayName: String): String {
        return when (displayName.trim()) {
            "Criminal Law" -> "CRIMINAL_LAW"
            "Civil Litigation" -> "CIVIL_LITIGATION"
            "Family Law" -> "FAMILY_LAW"
            "Corporate Law" -> "CORPORATE_LAW"
            "Tax Law" -> "TAX_LAW"
            "Intellectual Property" -> "INTELLECTUAL_PROPERTY"
            "Immigration Law" -> "IMMIGRATION_LAW"
            "Real Estate Law" -> "REAL_ESTATE_LAW"
            "Employment Law", "Labor Law" -> "EMPLOYMENT_LAW"
            "Environmental Law" -> "ENVIRONMENTAL_LAW"
            "Bankruptcy Law" -> "BANKRUPTCY_LAW"
            "Personal Injury" -> "PERSONAL_INJURY"
            "Medical Malpractice" -> "MEDICAL_MALPRACTICE"
            "Estate Planning" -> "ESTATE_PLANNING"
            "Elder Law" -> "ELDER_LAW"
            "Constitutional Law" -> "CONSTITUTIONAL_LAW"
            "International Law" -> "INTERNATIONAL_LAW"
            "Securities Law" -> "SECURITIES_LAW"
            "Consumer Protection" -> "CONSUMER_PROTECTION"
            "Contract Law" -> "CONTRACT_LAW"
            "Education Law" -> "EDUCATION_LAW"
            "Entertainment Law" -> "ENTERTAINMENT_LAW"
            "Sports Law" -> "SPORTS_LAW"
            "Military Law" -> "MILITARY_LAW"
            "Administrative Law" -> "ADMINISTRATIVE_LAW"
            "Healthcare Law" -> "HEALTHCARE_LAW"
            "Insurance Law" -> "INSURANCE_LAW"
            "Construction Law" -> "CONSTRUCTION_LAW"
            "Maritime Law" -> "MARITIME_LAW"
            "Human Rights Law" -> "HUMAN_RIGHTS_LAW"
            "Social Security Law" -> "SOCIAL_SECURITY_LAW"
            "Product Liability" -> "PRODUCT_LIABILITY"
            "Municipal Law" -> "MUNICIPAL_LAW"
            "Agricultural Law" -> "AGRICULTURAL_LAW"
            "Cyber Law" -> "CYBER_LAW"
            "Data Privacy Law" -> "DATA_PRIVACY_LAW"
            "Aviation Law" -> "AVIATION_LAW"
            "Animal Law" -> "ANIMAL_LAW"
            else -> displayName.uppercase().replace(" ", "_") // Fallback, but might not work if not in enum
        }
    }

    private fun handleError(e: Throwable) {
        val bodyText = (e as? HttpException)
            ?.response()?.errorBody()?.string()
            .orEmpty()

        val finalMsg = when {
            bodyText.isNotBlank() -> bodyText
            !e.message.isNullOrBlank() -> e.message!!
            else -> "Unknown error"
        }

        _uiState.value = SignUpUiState.Error(finalMsg)
    }

    fun resetState() {
        _uiState.value = SignUpUiState.Idle
    }
}

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    object Success : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}