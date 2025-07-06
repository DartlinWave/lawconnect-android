package com.qu3dena.lawconnect.android.shared.contracts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Contract for feature navigation graphs.
 * 
 * This interface defines the contract that all feature navigation graphs must implement.
 * It follows Clean Architecture principles by being a pure abstraction in the shared layer.
 * 
 * The additional parameters allow features to receive callbacks or data from the core
 * without breaking the dependency inversion principle.
 */
interface FeatureNavGraph {
    fun build(
        builder: NavGraphBuilder, 
        navController: NavHostController,
        additionalParams: Map<String, Any> = emptyMap()
    )
}