package com.example.orb_ed.presentation.screens.auth.forgotpassword

/**
 * Represents user intents for the Forgot Password screen.
 */
sealed class ForgotPasswordIntent {
    data class EmailChanged(val email: String) : ForgotPasswordIntent()
    object SendOtp : ForgotPasswordIntent()
    object NavigateToLogin : ForgotPasswordIntent()
}
