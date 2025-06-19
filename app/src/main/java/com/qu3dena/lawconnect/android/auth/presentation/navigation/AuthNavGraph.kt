package com.qu3dena.lawconnect.android.auth.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.qu3dena.lawconnect.android.auth.presentation.ui.screen.SignInView
import com.qu3dena.lawconnect.android.auth.presentation.ui.screen.SignUpStep1View
import com.qu3dena.lawconnect.android.auth.presentation.ui.screen.SignUpStep2View
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.SignUpUiState
import com.qu3dena.lawconnect.android.auth.presentation.ui.viewmodel.SignUpViewModel

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("sign_in")
    object SignUp : AuthScreen("sign_up")
}

sealed class SignUpStep(val route: String) {
    object Step1 : SignUpStep("sign_up_step1")
    object Step2 : SignUpStep("sign_up_step2")
}

fun NavGraphBuilder.authNavGraph(
    route: String,
    navController: NavHostController
) {
    navigation(
        route = route,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(AuthScreen.SignIn.route) {
            SignInView(
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }

        signUpNavGraph(
            route = AuthScreen.SignUp.route,
            navController = navController
        )
    }
}

private fun NavGraphBuilder.signUpNavGraph(
    route: String,
    navController: NavHostController
) {
    navigation(
        route = route,
        startDestination = SignUpStep.Step1.route
    ) {
        // --- STEP 1 ---
        composable(SignUpStep.Step1.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }
            val viewModel: SignUpViewModel = hiltViewModel(parentEntry)

            SignUpStep1View(
                username = viewModel.username,
                onUsernameChange = { viewModel.username = it },
                password = viewModel.password,
                onPasswordChange = { viewModel.password = it },
                confirmPass = viewModel.confirmPass,
                onConfirmPassChange = { viewModel.confirmPass = it },
                onNext = {
                    navController.navigate(SignUpStep.Step2.route)
                }
            )
        }

        // --- STEP 2 ---
        composable(SignUpStep.Step2.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

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
                        popUpTo(route) { inclusive = true }
                        launchSingleTop = true
                    }
                    viewModel.resetState()
                }
            )
        }
    }
}
