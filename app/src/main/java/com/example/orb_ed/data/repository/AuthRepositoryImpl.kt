package com.example.orb_ed.data.repository

import android.util.Patterns
import com.example.orb_ed.domain.repository.AuthRepository
import com.example.orb_ed.domain.usecase.auth.AuthResult
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AuthRepository] that handles authentication operations.
 * This is a simulated implementation for development purposes.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    // Simulated in-memory storage for demo purposes
    private val userDatabase = mutableMapOf<String, UserCredentials>()
    private val otpStore = mutableMapOf<String, String>()
    private var currentUser: String? = null

    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        // Simulate network delay
        delay(1000)

        return try {
            // Check if user exists and password matches
            val user = userDatabase[email]
            if (user != null && user.password == password) {
                currentUser = email
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Invalid email or password")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        phoneNumber: String,
        institution: String
    ): AuthResult<Unit> {
        try {
            // Simulate network delay
            delay(1000)

            // Check if user already exists
            if (userDatabase.containsKey(email)) {
                return AuthResult.Error("User with this email already exists")
            }

            // Create new user
            userDatabase[email] = UserCredentials(email, password, phoneNumber, institution)
            currentUser = email

            // Generate and store OTP for verification (in a real app, this would be sent via email/SMS)
            val otp = (100000..999999).random().toString()
            otpStore[email] = otp

            return AuthResult.Success(Unit)
        } catch (e: Exception) {
            return AuthResult.Error(e.message ?: "Sign up failed")
        }
    }


    override suspend fun forgotPassword(email: String): AuthResult<Unit> {
        // Simulate network delay
        delay(1000)

        return try {
            if (userDatabase.containsKey(email)) {
                // Generate and store OTP for password reset
                val otp = (100000..999999).random().toString()
                otpStore[email] = otp
                // In a real app, send OTP to user's email/phone
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("No account found with this email")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to process password reset")
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): AuthResult<Unit> {
        // Simulate network delay
        delay(1000)

        return try {
            val storedOtp = otpStore[email]
            if (storedOtp == otp) {
                // Clear OTP after successful verification
                otpStore.remove(email)
                AuthResult.Success(Unit)
            } else {
                AuthResult.Error("Invalid or expired OTP")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "OTP verification failed")
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return currentUser != null
    }

    override suspend fun logout() {
        currentUser = null
    }

    // Data class to store user credentials in memory
    private data class UserCredentials(
        val email: String,
        val password: String,
        val phoneNumber: String,
        val institution: String
    )
}

