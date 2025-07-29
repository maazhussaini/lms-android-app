package com.example.orb_ed.data.repository

import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.manager.UserManager
import com.example.orb_ed.data.remote.api.ApiService
import com.example.orb_ed.data.remote.model.auth.ErrorResponse
import com.example.orb_ed.data.remote.model.auth.LoginRequest
import com.example.orb_ed.data.remote.model.auth.LoginResponse
import com.example.orb_ed.domain.repository.AuthRepository
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.util.Constants.TENANT_CONTEXT
import com.example.orb_ed.util.NetworkUtils
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AuthRepository] that handles authentication operations.
 *
 * @property apiService The API service for making network requests.
 * @property tokenManager Manages authentication tokens.
 * @property userManager Manages user data.
 * @property networkUtils Provides network-related utility functions.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val userManager: UserManager,
    private val networkUtils: NetworkUtils
) : AuthRepository {

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AuthResult] indicating success or failure.
     */
    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        // Validate input
        if (email.isBlank() || password.isBlank()) {
            return AuthResult.Error("Email and password cannot be empty")
        }

        // Check network connectivity
        if (!networkUtils.isNetworkAvailable()) {
            return AuthResult.Error("No internet connection")
        }

        // Create login request with hardcoded tenant context
        val loginRequest = LoginRequest(
            emailAddress = email,
            password = password,
            tenantContext = TENANT_CONTEXT
        )

        // Make API call with error handling
        return try {
            val response = apiService.login(loginRequest)
            handleLoginResponse(response)
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    /**
     * Handles the login response from the API.
     *
     * @param response The API response.
     * @return [Unit] on success, throws [Exception] on failure.
     * @throws Exception If the response is not successful or data is invalid.
     */
    private suspend fun handleLoginResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            val loginResponse = response.body()

            if (loginResponse?.success == true) {
                // Save tokens if available
                loginResponse.data?.tokens?.let { tokens ->
                    tokenManager.saveTokens(
                        accessToken = tokens.accessToken,
                        refreshToken = tokens.refreshToken,
                        expiresIn = tokens.expiresIn ?: 3600 // Default to 1 hour if not provided
                    )
                } ?: throw Exception("No tokens in response")

                // Save user data if available
                loginResponse.data.user?.let { user ->
                    userManager.saveUser(user)
                } ?: throw Exception("No user data in response")

                return // Success
            } else {
                throw Exception(loginResponse?.message ?: "Login failed")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let {
                try {
                    Json.decodeFromString<ErrorResponse>(it)
                } catch (e: Exception) {
                    null
                }
            }

            throw Exception(
                errorResponse?.message
                    ?: "Login failed: ${response.code()} - ${errorBody ?: "Unknown error"}"
            )
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        phoneNumber: String,
        institution: String
    ): AuthResult<Unit> {
        return try {
            // TODO: Implement actual signup logic with API
            AuthResult.Error("Signup not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun forgotPassword(email: String): AuthResult<Unit> {
        return try {
            // TODO: Implement actual forgot password logic with API
            AuthResult.Error("Forgot password not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to process password reset")
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): AuthResult<Unit> {
        return try {
            // TODO: Implement actual OTP verification logic with API
            AuthResult.Error("OTP verification not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "OTP verification failed")
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenManager.accessToken != null && !tokenManager.isTokenExpired()
    }

    override suspend fun logout() {
        // Clear tokens
        tokenManager.clearTokens()
        // Clear user data
        userManager.clearUser()
    }

    override suspend fun resetPassword(newPassword: String): AuthResult<Unit> {
        return try {
            // TODO: Implement actual password reset logic with API
            AuthResult.Error("Password reset not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to reset password")
        }
    }
}

