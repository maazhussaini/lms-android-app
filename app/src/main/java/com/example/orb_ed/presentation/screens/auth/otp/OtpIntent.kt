package com.example.orb_ed.presentation.screens.auth.otp

/**
 * Represents user intents/actions for the OTP verification screen.
 */
sealed class OtpIntent {
    data class OtpChanged(val otp: String) : OtpIntent()
    object VerifyOtp : OtpIntent()
    object ResendOtp : OtpIntent()
    object StartCountdown : OtpIntent()
    object UpdateTimer : OtpIntent()
}
