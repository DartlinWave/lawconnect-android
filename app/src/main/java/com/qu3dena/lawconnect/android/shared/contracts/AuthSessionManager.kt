package com.qu3dena.lawconnect.android.shared.contracts

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
} 