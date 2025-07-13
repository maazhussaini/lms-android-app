package com.example.orb_ed.domain.usecase.auth

import android.util.Patterns
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
     * @param email The user's email address.
     * @param password The user's chosen password.
     * @param confirmPassword Confirmation of the user's password.
     * @param phoneNumber The user's phone number.
     * @param institution The user's institution name.
     * @return [AuthResult] containing the result of the sign-up operation.
     */
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String,
        institution: String
    ): AuthResult<Unit> {
        // Validate input
        if (email.isBlank()) {
            return AuthResult.Error("Email cannot be empty")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult.Error("Please enter a valid email address")
        }
        if (password != confirmPassword) {
            return AuthResult.Error("Passwords do not match")
        }
        if (password.length < 6) {
            return AuthResult.Error("Password must be at least 6 characters")
        }
        if (phoneNumber.isBlank()) {
            return AuthResult.Error("Phone number cannot be empty")
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            return AuthResult.Error("Please enter a valid phone number")
        }
        if (institution.isBlank()) {
            return AuthResult.Error("Institute cannot be empty")
        }
        
        // Call repository to perform sign-up
        return authRepository.signUp(email, password, phoneNumber, institution)
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Remove all non-digit characters
        val digitsOnly = phoneNumber.filter { it.isDigit() }

        // Check if it has at least 10 digits
        return digitsOnly.length >= 10
    }
}