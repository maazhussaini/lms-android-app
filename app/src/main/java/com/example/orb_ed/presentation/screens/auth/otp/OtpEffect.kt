package com.example.orb_ed.presentation.screens.auth.otp

/**
 * Represents side effects for the OTP verification screen.
 */
sealed class OtpEffect {
    object NavigateToNextScreen : OtpEffect()
    object ShowLoading : OtpEffect()
    object HideLoading : OtpEffect()
    data class ShowError(val message: String) : OtpEffect()
    data class ShowResendSuccess(val message: String) : OtpEffect()
}
