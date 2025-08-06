package com.example.orb_ed.presentation.screens.auth.login

/**
 * Represents the UI state for the Login screen.
 */
data class LoginState(
    val email: String = "sarah.johnson@alpha-academy.edu",
    val password: String = "Password123!",
    val isPasswordVisible: Boolean = false,
)
