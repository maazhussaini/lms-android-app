package com.example.orb_ed.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.orb_ed.presentation.screens.auth.forgotpassword.ForgotPasswordScreen
import com.example.orb_ed.presentation.screens.auth.forgotpassword.ForgotPasswordViewModel
import com.example.orb_ed.presentation.screens.auth.login.LoginScreen
import com.example.orb_ed.presentation.screens.auth.login.LoginViewModel
import com.example.orb_ed.presentation.screens.auth.otp.OtpVerificationScreen
import com.example.orb_ed.presentation.screens.auth.signup.SignUpViewModel
import com.example.orb_ed.presentation.screens.auth.signup.SignupScreen
import com.example.orb_ed.presentation.screens.home.HomeScreen
import com.example.orb_ed.presentation.screens.splash.SplashScreen

/**
 * Main navigation graph for the application.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToHome = { navController.navigate(Screen.Home.route) }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val effect = viewModel.effect
            LoginScreen(
                uiState = uiState,
                effect = effect,
                onIntent = viewModel::sendIntent,
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { navController.navigate(Screen.Home.route) }
            )
        }

        // Sign Up Screen
        composable(Screen.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val effect = viewModel.effect
            SignupScreen(
                uiState,
                effect,
                viewModel::sendIntent,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToNextScreen = {})
        }

        // Forgot Password Screen
        composable(Screen.ForgotPassword.route) {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            ForgotPasswordScreen(
                uiState = uiState,
                effect = viewModel.effect,
                onIntent = viewModel::sendIntent,
                onNavigateBack = { navController.popBackStack() },
                onSendOtp = { email ->
                    navController.navigate(Screen.OtpVerification.createRoute(email))
                }
            )
        }

        // OTP Verification Screen
        composable(
            route = Screen.OtpVerification.route,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpVerificationScreen(
                email = email,
                onVerifySuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
