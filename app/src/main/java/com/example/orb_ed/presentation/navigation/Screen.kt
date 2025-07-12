package com.example.orb_ed.presentation.navigation

/**
 * Sealed class representing all screens in the app.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object OtpVerification : Screen("otp_verification/{email}") {
        fun createRoute(email: String) = "otp_verification/${email}"
    }
    object Home : Screen("home")
}
