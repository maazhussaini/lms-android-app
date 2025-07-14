package com.example.orb_ed.presentation.screens.auth.otp

/**
 * Represents the UI state for the OTP verification screen.
 */
data class OtpState(
    val otp: String = "".take(6),
    val isOtpValid: Boolean = false,
    val isResendEnabled: Boolean = false,
    val timeRemaining: String = "05:00",
    val isLoading: Boolean = false,
    val email: String = ""
)
