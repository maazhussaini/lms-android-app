package com.example.orb_ed.presentation.screens.auth.forgotpassword

/**
 * Represents side effects for the Forgot Password screen.
 */
sealed class ForgotPasswordEffect {
    object NavigateToLogin : ForgotPasswordEffect()
    object ShowLoading : ForgotPasswordEffect()
    object HideLoading : ForgotPasswordEffect()
    data class ShowError(val message: String) : ForgotPasswordEffect()
    data class ShowSuccess(val message: String) : ForgotPasswordEffect()
}
