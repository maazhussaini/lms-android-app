package com.example.orb_ed.presentation.screens.auth.resetpassword

/**
 * Represents side effects for the Reset Password screen.
 */
sealed class ResetPasswordEffect {
    object NavigateToLogin : ResetPasswordEffect()
    object ShowLoading : ResetPasswordEffect()
    object HideLoading : ResetPasswordEffect()
    data class ShowError(val message: String) : ResetPasswordEffect()
    data class ShowSuccess(val message: String) : ResetPasswordEffect()
}
