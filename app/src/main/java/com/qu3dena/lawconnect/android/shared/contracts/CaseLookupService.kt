package com.qu3dena.lawconnect.android.shared.contracts

interface CaseLookupService {
    suspend fun getCaseTitles(): List<String>
    suspend fun getCaseStatuses(): List<String>
    suspend fun getInvitedCaseTitles(): List<String>
    suspend fun getInvitedCaseStatuses(): List<String>
}