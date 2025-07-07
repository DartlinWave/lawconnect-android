package com.qu3dena.lawconnect.android.features.auth.domain.model

data class ContactInfo(
    val phoneNumber: String,
    val address: String
)

data class LawyerProfileRequest(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val dni: String,
    val contactInfo: ContactInfo,
    val description: String,
    val specialties: List<String>
)

data class FullName(
    val firstname: String,
    val lastname: String
)

data class LawyerProfileResponse(
    val id: String,
    val userId: String,
    val fullName: FullName,
    val dni: String,
    val contactInfo: ContactInfo,
    val description: String,
    val specialties: List<String>
)
