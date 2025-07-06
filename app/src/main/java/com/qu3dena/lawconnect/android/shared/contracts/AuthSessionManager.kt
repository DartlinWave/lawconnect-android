package com.qu3dena.lawconnect.android.shared.contracts

import kotlinx.coroutines.flow.StateFlow

/**
 * Contract for authentication session management that the core module needs.
 * 
 * This interface follows Clean Architecture principles by:
 * - Being a pure abstraction in the shared layer
 * - Defining only the session management operations that core needs
 * - Allowing the auth feature to implement this contract
 * - Keeping core independent of specific feature implementations
 */
interface AuthSessionManager {
    /**
     * Signs out the current user and clears the session.
     */
    fun signOut()
    
    /**
     * Returns the current login state as a StateFlow.
     */
    fun getIsLoggedInState(): StateFlow<Boolean>
    
    /**
     * Returns the current username as a StateFlow.
     * Can be null if no user is logged in.
     */
    fun getUsernameLoggedState(): StateFlow<String?>
} 