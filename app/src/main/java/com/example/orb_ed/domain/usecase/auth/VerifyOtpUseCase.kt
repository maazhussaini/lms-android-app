package com.example.orb_ed.domain.usecase.auth

import com.example.orb_ed.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for verifying OTP (One-Time Password) during authentication.
 *
 * @property authRepository The authentication repository to handle OTP verification.
 */
class VerifyOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Verifies the provided OTP for the given email.
     *
     * @param email The email address associated with the OTP.
     * @param otp The one-time password to verify.
     * @return [AuthResult] indicating whether the OTP was successfully verified.
     */
    suspend operator fun invoke(email: String, otp: String): AuthResult<Unit> {
        // Validate input
        if (email.isBlank()) {
            return AuthResult.Error("Email cannot be empty")
        }
        if (otp.isBlank() || otp.length != 6 || !otp.matches(Regex("\\d+"))) {
            return AuthResult.Error("Please enter a valid 6-digit OTP")
        }
        
        // Call repository to verify OTP and return the result directly
        return when (val result = authRepository.verifyOtp(email, otp)) {
            is AuthResult.Success -> AuthResult.Success(Unit)
            is AuthResult.Error -> result
            is AuthResult.Loading -> AuthResult.Error("Unexpected loading state")
        }
    }
}
