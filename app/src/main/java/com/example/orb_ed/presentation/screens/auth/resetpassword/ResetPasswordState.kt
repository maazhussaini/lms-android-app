package com.example.orb_ed.presentation.screens.auth.resetpassword

/**
 * Represents the UI state for the Reset Password screen.
 */
data class ResetPasswordState(
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
)
