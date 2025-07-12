package com.example.orb_ed.domain.repository

import com.example.orb_ed.domain.usecase.auth.AuthResult

/**
 * Repository interface for authentication-related operations.
 */
interface AuthRepository {
    /**
     * Logs in a user with the provided credentials.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AuthResult] containing the success status and optional error message.
     */
    suspend fun login(email: String, password: String): AuthResult<Unit>
    
    /**
     * Registers a new user with the provided details.
     *
     * @param name The user's full name.
     * @param email The user's email address.
     * @param password The user's password.
     * @return [AuthResult] containing the success status and optional error message.
     */
    suspend fun signUp(name: String, email: String, password: String): AuthResult<Unit>
    
    /**
     * Sends a password reset OTP to the provided email address.
     *
     * @param email The email address to send the OTP to.
     * @return [AuthResult] containing the success status and optional error message.
     */
    suspend fun forgotPassword(email: String): AuthResult<Unit>
    
    /**
     * Verifies the OTP sent to the user's email.
     *
     * @param email The user's email address.
     * @param otp The OTP to verify.
     * @return [AuthResult] containing the success status and optional error message.
     */
    suspend fun verifyOtp(email: String, otp: String): AuthResult<Unit>
    
    /**
     * Logs out the current user.
     */
    suspend fun logout()
    
    /**
     * Checks if a user is currently logged in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    suspend fun isLoggedIn(): Boolean
}

/**
 * A generic class that holds a value with its loading status.
 * @param T The type of data to be returned.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String? = null) : Result<Nothing>()
    
    val isSuccess: Boolean get() = this is Success<T>
    val isError: Boolean get() = this is Error
    val errorMessage: String? get() = (this as? Error)?.message
}
