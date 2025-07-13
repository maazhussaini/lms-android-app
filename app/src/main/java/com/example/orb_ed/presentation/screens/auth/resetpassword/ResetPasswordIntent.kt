package com.example.orb_ed.presentation.screens.auth.resetpassword

/**
 * Represents user intents/actions for the Reset Password screen.
 */
sealed class ResetPasswordIntent {
    data class PasswordChanged(val password: String) : ResetPasswordIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ResetPasswordIntent()
    object TogglePasswordVisibility : ResetPasswordIntent()
    object ToggleConfirmPasswordVisibility : ResetPasswordIntent()
    object ResetPassword : ResetPasswordIntent()
    object NavigateToLogin : ResetPasswordIntent()
}
