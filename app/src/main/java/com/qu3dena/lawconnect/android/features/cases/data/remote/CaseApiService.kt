package com.qu3dena.lawconnect.android.features.cases.data.remote

import com.qu3dena.lawconnect.android.features.cases.data.entities.CaseDto
import com.qu3dena.lawconnect.android.features.cases.data.entities.InvitedCaseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CaseApiService {

    @GET("cases/suggested")
    suspend fun getSuggestedCases(
        @Query("lawyerId") lawyerId: String
    ): List<CaseDto>

    @GET("invitations")
    suspend fun getInvitedCases(
        @Query("lawyerId") lawyerId: String
    ) : List<InvitedCaseDto>

    @GET("cases/{caseId}")
    suspend fun getCaseById(
        @Path("caseId") caseId: String
    ): CaseDto
}