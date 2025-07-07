package com.qu3dena.lawconnect.android.features.auth.data.entities

data class ContactInfoDto(
    val phoneNumber: String,
    val address: String
)

data class FullNameDto(
    val firstname: String,
    val lastname: String
)

data class LawyerProfileRequestDto(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val dni: String,
    val contactInfo: ContactInfoDto,
    val description: String,
    val specialties: List<String>
)

data class LawyerProfileResponseDto(
    val id: String,
    val userId: String,
    val fullName: FullNameDto,
    val dni: String,
    val contactInfo: ContactInfoDto,
    val description: String,
    val specialties: List<String>
)
