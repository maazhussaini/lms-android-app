package com.example.orb_ed.domain.usecase.auth

import com.example.orb_ed.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for user registration functionality.
 *
 * @property authRepository The authentication repository to handle sign-up operations.
 */
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Executes the sign-up operation with the provided user details.
     *
     * @param name The user's full name.
     * @param email The user's email address.
     * @param password The user's chosen password.
     * @param confirmPassword Confirmation of the user's password.
     * @return [AuthResult] containing the result of the sign-up operation.
     */
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): AuthResult<Unit> {
        // Validate input
        if (name.isBlank()) {
            return AuthResult.Error("Name cannot be empty")
        }
        if (email.isBlank()) {
            return AuthResult.Error("Email cannot be empty")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult.Error("Please enter a valid email address")
        }
        if (password.length < 8) {
            return AuthResult.Error("Password must be at least 8 characters long")
        }
        if (password != confirmPassword) {
            return AuthResult.Error("Passwords do not match")
        }
        
        // Call repository to perform sign-up
        return when (val result = authRepository.signUp(name, email, password)) {
            is AuthResult.Success -> AuthResult.Success(Unit)
            is AuthResult.Error -> result
            is AuthResult.Loading -> AuthResult.Error("Unexpected loading state")
        }
    }
}
