package com.example.orb_ed.domain.usecase.auth

import com.example.orb_ed.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for resetting user password.
 */
class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        password: String,
        confirmPassword: String
    ): AuthResult<Unit> {
        // Validate password and confirm password match
        if (password != confirmPassword) {
            return AuthResult.Error("Passwords don't match")
        }

        // Validate password strength
        if (password.length < 8) {
            return AuthResult.Error("Password must be at least 8 characters")
        }

        // Call repository to reset password
        return when (val result = authRepository.resetPassword(password)) {
            is AuthResult.Success -> AuthResult.Success(Unit)
            is AuthResult.Error -> result
            is AuthResult.Loading -> AuthResult.Error("Unexpected loading state")
        }
    }
}
