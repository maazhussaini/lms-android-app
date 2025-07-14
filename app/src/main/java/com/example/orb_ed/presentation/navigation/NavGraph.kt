package com.example.orb_ed.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.orb_ed.R
import com.example.orb_ed.presentation.screens.auth.forgotpassword.ForgotPasswordScreen
import com.example.orb_ed.presentation.screens.auth.forgotpassword.ForgotPasswordViewModel
import com.example.orb_ed.presentation.screens.auth.login.LoginScreen
import com.example.orb_ed.presentation.screens.auth.login.LoginViewModel
import com.example.orb_ed.presentation.screens.auth.otp.OtpScreen
import com.example.orb_ed.presentation.screens.auth.otp.OtpViewModel
import com.example.orb_ed.presentation.screens.auth.signup.SignUpViewModel
import com.example.orb_ed.presentation.screens.auth.signup.SignupScreen
import com.example.orb_ed.presentation.screens.splash.SplashScreen

/**
 * Main navigation graph for the application using type-safe navigation.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        // Splash Screen
        composable<Splash> { backStackEntry ->
            SplashScreen(
                onNavigateToLogin = { navController.navigate(Login) },
                onNavigateToHome = { navController.navigate(Home) }
            )
        }

        // Login Screen
        composable<Login> { backStackEntry ->
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val effect = viewModel.effect
            
            LoginScreen(
                uiState = uiState,
                effect = effect,
                onIntent = viewModel::sendIntent,
                onNavigateToSignUp = { navController.navigate(SignUp) },
                onNavigateToForgotPassword = { navController.navigate(ForgotPassword) },
                onLoginSuccess = { 
                    navController.navigate(Home) {
                        // Clear the back stack to prevent going back to login
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Sign Up Screen
        composable<SignUp> { backStackEntry ->
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val effect = viewModel.effect
            
            SignupScreen(
                uiState = uiState,
                effect = effect,
                onIntent = viewModel::sendIntent,
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo(0) { inclusive = false }
                    }
                },
                onNavigateToNextScreen = {
                    // Handle successful signup navigation if needed
                }
            )
        }

        // Forgot Password Screen
        composable<ForgotPassword> { backStackEntry ->
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            ForgotPasswordScreen(
                uiState = uiState,
                effect = viewModel.effect,
                onIntent = viewModel::sendIntent,
                onNavigateBack = { navController.popBackStack() },
                onSendOtp = { email ->
                    navController.navigate(OTP(email))
                }
            )
        }

        // OTP Verification Screen
        composable<OTP> { backStackEntry ->
            val otpData = backStackEntry.toRoute<OTP>()
            val email = otpData.email
            val viewModel: OtpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            // Set email in ViewModel when screen is first displayed
            LaunchedEffect(Unit) {
                viewModel.setEmail(email)
            }
            
            OtpScreen(
                uiState = uiState,
                effect = viewModel.effect,
                onIntent = viewModel::handleIntent,
                onNavigateBackToLogin = { navController.popBackStack() },
                onNavigateToNext = { /* Handle successful verification */ }
            )
        }
        
        // Home Screen
        composable<Home> { backStackEntry ->
            // Add your Home screen composable here when ready
            // For now, it's a placeholder
            androidx.compose.material3.Text(stringResource(id = R.string.home_screen))
        }
    }
}
