package com.qu3dena.lawconnect.android.features.auth.data.entities

import com.qu3dena.lawconnect.android.features.auth.domain.model.ContactInfo
import com.qu3dena.lawconnect.android.features.auth.domain.model.FullName
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileRequest
import com.qu3dena.lawconnect.android.features.auth.domain.model.LawyerProfileResponse

fun ContactInfo.toContactInfoDto(): ContactInfoDto {
    return ContactInfoDto(
        phoneNumber = phoneNumber,
        address = address
    )
}

fun ContactInfoDto.toContactInfo(): ContactInfo {
    return ContactInfo(
        phoneNumber = phoneNumber,
        address = address
    )
}

fun FullNameDto.toFullName(): FullName {
    return FullName(
        firstname = firstname,
        lastname = lastname
    )
}

fun LawyerProfileRequest.toLawyerProfileRequestDto(): LawyerProfileRequestDto {
    return LawyerProfileRequestDto(
        userId = userId,
        firstname = firstname,
        lastname = lastname,
        dni = dni,
        contactInfo = contactInfo.toContactInfoDto(),
        description = description,
        specialties = specialties
    )
}

fun LawyerProfileResponseDto.toLawyerProfileResponse(): LawyerProfileResponse {
    return LawyerProfileResponse(
        id = id,
        userId = userId,
        fullName = fullName.toFullName(),
        dni = dni,
        contactInfo = contactInfo.toContactInfo(),
        description = description,
        specialties = specialties
    )
}
