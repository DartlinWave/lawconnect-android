package com.qu3dena.lawconnect.android.features.auth.presentation.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens.SignInView
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens.SignUpStep1View
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.screens.SignUpStep2View
import com.qu3dena.lawconnect.android.features.auth.presentation.ui.viewmodels.SignUpViewModel
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph

/**
 * Authentication feature navigation routes.
 */
sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("sign_in")
    object SignUp : AuthScreen("sign_up")
}

/**
 * Sign-up step navigation routes.
 */
sealed class SignUpStep(val route: String) {
    object Step1 : SignUpStep("sign_up_step1")
    object Step2 : SignUpStep("sign_up_step2")
}

/**
 * Authentication feature navigation graph implementation.
 * 
 * This class implements the FeatureNavGraph contract and provides
 * all authentication-related navigation routes.
 */
class AuthNavGraph : FeatureNavGraph {
    
    @SuppressLint("UnrememberedGetBackStackEntry")
    override fun build(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            route = "auth_graph",
            startDestination = AuthScreen.SignIn.route
        ) {
            // Sign In Screen
            composable(route = AuthScreen.SignIn.route) {
                SignInView(
                    onSuccess = { /* Define sign-in success logic */ },
                    onSignUpClick = { navController.navigate(AuthScreen.SignUp.route) }
                )
            }

            // Sign Up Flow
            navigation(
                route = AuthScreen.SignUp.route,
                startDestination = SignUpStep.Step1.route
            ) {
                // Step 1: Basic Information
                composable(route = SignUpStep.Step1.route) {
                    val parentEntry = navController.getBackStackEntry(AuthScreen.SignUp.route)
                    val viewModel: SignUpViewModel = hiltViewModel(parentEntry)
                    SignUpStep1View(
                        username = viewModel.username,
                        onUsernameChange = { viewModel.username = it },
                        password = viewModel.password,
                        onPasswordChange = { viewModel.password = it },
                        confirmPass = viewModel.confirmPass,
                        onConfirmPassChange = { viewModel.confirmPass = it },
                        onNext = { navController.navigate(SignUpStep.Step2.route) }
                    )
                }
                
                // Step 2: Profile Information
                composable(route = SignUpStep.Step2.route) {
                    val parentEntry = navController.getBackStackEntry(AuthScreen.SignUp.route)
                    val viewModel: SignUpViewModel = hiltViewModel(parentEntry)
                    val uiState by viewModel.uiState
                    val context = LocalContext.current
                    SignUpStep2View(
                        uiState = uiState,
                        firstName = viewModel.firstName,
                        onFirstNameChange = { viewModel.firstName = it },
                        lastName = viewModel.lastName,
                        onLastNameChange = { viewModel.lastName = it },
                        description = viewModel.description,
                        onDescriptionChange = { viewModel.description = it },
                        specialties = viewModel.specialties,
                        onSpecialtiesChange = { viewModel.specialties = it },
                        onSignUpClick = { viewModel.signUp() },
                        onBackClick = { navController.popBackStack() },
                        onShowError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        },
                        onSuccess = {
                            navController.navigate(AuthScreen.SignIn.route) {
                                popUpTo("auth_graph") { inclusive = true }
                                launchSingleTop = true
                            }
                            viewModel.resetState()
                        }
                    )
                }
            }
        }
    }
}