package com.example.orb_ed.domain.usecase.auth

import com.example.orb_ed.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for handling forgot password functionality.
 *
 * @property authRepository The authentication repository to handle password reset operations.
 */
class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Initiates the password reset process for the given email.
     *
     * @param email The email address for which to reset the password.
     * @return [AuthResult] indicating the result of the password reset request.
     */
    suspend operator fun invoke(email: String): AuthResult<Unit> {
        // Validate input
        if (email.isBlank()) {
            return AuthResult.Error("Email cannot be empty")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult.Error("Please enter a valid email address")
        }
        
        // Call repository to initiate password reset
        return when (val result = authRepository.forgotPassword(email)) {
            is AuthResult.Success -> AuthResult.Success(Unit)
            is AuthResult.Error -> result
            is AuthResult.Loading -> AuthResult.Error("Unexpected loading state")
        }
    }
}
