package com.example.orb_ed.presentation.screens.auth.signup

sealed class SignUpEffect {
    data class NavigateToNextScreen(val data: Any? = null) : SignUpEffect()
    object NavigateToLogin : SignUpEffect()
    object ShowLoading : SignUpEffect()
    object HideLoading : SignUpEffect()
    data class ShowError(val message: String) : SignUpEffect()
}
