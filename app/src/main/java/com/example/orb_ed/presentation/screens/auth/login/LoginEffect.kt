package com.example.orb_ed.presentation.screens.auth.login

/**
 * Represents side effects for the Login screen.
 */
sealed class LoginEffect {
    data class NavigateToNextScreen(val data: Any? = null) : LoginEffect()
    object NavigateToSignup : LoginEffect()
    object NavigateToForgotPassword : LoginEffect()
    object ShowLoading : LoginEffect()
    object HideLoading : LoginEffect()
    data class ShowError(val message: String) : LoginEffect()
}
