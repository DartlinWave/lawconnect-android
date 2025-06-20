package com.qu3dena.lawconnect.android.home.data.source.remote

import com.qu3dena.lawconnect.android.home.data.model.InvitedCaseDto
import com.qu3dena.lawconnect.android.home.data.model.CaseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SummaryApiService {

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