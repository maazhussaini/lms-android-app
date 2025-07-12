package com.example.orb_ed.domain.usecase.auth

import com.example.orb_ed.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for user login functionality.
 *
 * @property authRepository The authentication repository to handle login operations.
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Executes the login operation with the provided credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AuthResult] containing the result of the login operation.
     */
    suspend operator fun invoke(email: String, password: String): AuthResult<Unit> {
        // Validate input
        if (email.isBlank() || password.isBlank()) {
            return AuthResult.Error("Email and password cannot be empty")
        }
        
        // Call repository to perform login
        return when (val result = authRepository.login(email, password)) {
            is AuthResult.Success -> AuthResult.Success(Unit)
            is AuthResult.Error -> result
            is AuthResult.Loading -> AuthResult.Error("Unexpected loading state")
        }
    }
}
