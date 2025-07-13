package com.example.orb_ed.presentation.screens.auth.login

/**
 * Represents user intents for the Login screen.
 */
sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    object TogglePasswordVisibility : LoginIntent()
    object Login : LoginIntent()
    object NavigateToSignup : LoginIntent()
    object NavigateToForgotPassword : LoginIntent()
}
