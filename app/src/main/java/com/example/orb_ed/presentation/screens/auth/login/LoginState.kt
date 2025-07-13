package com.example.orb_ed.presentation.screens.auth.login

/**
 * Represents the UI state for the Login screen.
 */
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
)
