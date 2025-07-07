package com.qu3dena.lawconnect.android.features.auth.data.remote

import com.qu3dena.lawconnect.android.shared.services.ApiService
import com.qu3dena.lawconnect.android.features.auth.data.di.AuthPreferences
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignInResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.SignUpResponse
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignUpRequestDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignInRequestDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.toSignUpResponse
import com.qu3dena.lawconnect.android.features.auth.data.entities.toSignInResponse
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignInResponseDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.SignUpResponseDto
import com.qu3dena.lawconnect.android.features.auth.domain.repository.AuthRepository
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileRequest
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileResponse
import com.qu3dena.lawconnect.android.features.auth.domain.model.ContactInfo
import com.qu3dena.lawconnect.android.features.auth.domain.model.FullName
import com.qu3dena.lawconnect.android.features.auth.data.entities.ContactInfoDto
import com.qu3dena.lawconnect.android.features.auth.data.entities.FullNameDto

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import android.util.Log

class AuthApiService @Inject constructor(
    private val api: ApiService,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override fun signUp(username: String, password: String, role: String): Flow<SignUpResponse> = flow {
        try {
            val request = SignUpRequestDto(username, password, role)
            val response = api.post<SignUpRequestDto, SignUpResponseDto>("authentication/sign-up", request)
            emit(response.toSignUpResponse())
        } catch (_: Exception) {
            throw Exception("Registration failed")
        }
    }.flowOn(Dispatchers.IO)

    override fun signIn(username: String, password: String): Flow<SignInResponse> = flow {
        try {
            val request = SignInRequestDto(username, password)
            val response = api.post<SignInRequestDto, SignInResponseDto>("authentication/sign-in", request)

            // Save authentication data
            authPreferences.saveToken(response.token)
            authPreferences.saveUsername(response.username)

            emit(response.toSignInResponse())
        } catch (e: Exception) {
            throw Exception("Login failed: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    override fun signOut(): Flow<Unit> = flow {
        try {
            authPreferences.clearToken()
            authPreferences.clearUsername()
            emit(Unit)
        } catch (_: Exception) {
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)

    override fun getToken(): Flow<String?> =
        authPreferences.token

    override fun getUsername(): Flow<String?> =
        authPreferences.username

    override fun createLawyerProfile(lawyerProfileRequest: LawyerProfileRequest): Flow<LawyerProfileResponse> = flow {
        try {
            Log.d("AuthApiService", "Creating lawyer profile for user: ${lawyerProfileRequest.userId}")
            val request = mapToRequestDto(lawyerProfileRequest)
            Log.d("AuthApiService", "Request mapped: $request")

            val responseDto = api.post<Map<String, Any>, Map<String, Any>>("lawyers", request)
            Log.d("AuthApiService", "Response received: $responseDto")

            val response = mapToResponse(responseDto)
            Log.d("AuthApiService", "Response mapped successfully")
            emit(response)
        } catch (e: Exception) {
            Log.e("AuthApiService", "Error creating lawyer profile", e)
            throw Exception("Lawyer profile creation failed: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    private fun mapToRequestDto(request: LawyerProfileRequest): Map<String, Any> {
        return mapOf(
            "userId" to request.userId,
            "firstname" to request.firstname,
            "lastname" to request.lastname,
            "dni" to request.dni,
            "contactInfo" to mapOf(
                "phoneNumber" to request.contactInfo.phoneNumber,
                "address" to request.contactInfo.address
            ),
            "description" to request.description,
            "specialties" to request.specialties
        )
    }

    private fun mapToResponse(responseMap: Map<String, Any>): LawyerProfileResponse {
        try {
            val fullNameMap = (responseMap["fullName"] as? Map<String, Any>) ?: mapOf<String, Any>()
            val contactInfoMap = (responseMap["contactInfo"] as? Map<String, Any>) ?: mapOf<String, Any>()

            return LawyerProfileResponse(
                id = (responseMap["id"] as? String) ?: "",
                userId = (responseMap["userId"] as? String) ?: "",
                fullName = FullName(
                    firstname = (fullNameMap["firstname"] as? String) ?: "",
                    lastname = (fullNameMap["lastname"] as? String) ?: ""
                ),
                dni = (responseMap["dni"] as? String) ?: "",
                contactInfo = ContactInfo(
                    phoneNumber = (contactInfoMap["phoneNumber"] as? String) ?: "",
                    address = (contactInfoMap["address"] as? String) ?: ""
                ),
                description = (responseMap["description"] as? String) ?: "",
                specialties = (responseMap["specialties"] as? List<*>)?.map { it.toString() } ?: emptyList()
            )
        } catch (e: Exception) {
            throw Exception("Failed to parse lawyer profile response: ${e.message}")
        }
    }
}
