package com.example.orb_ed.domain.usecase.auth

/**
 * Sealed class representing the result of an authentication operation.
 */
sealed class AuthResult<out T> {
    /**
     * Represents a successful authentication operation with the provided [data].
     */
    data class Success<T>(val data: T) : AuthResult<T>() 
    
    /**
     * Represents a failed authentication operation with the provided [errorMessage].
     */
    data class Error(val errorMessage: String) : AuthResult<Nothing>()
    
    /**
     * Represents an authentication operation that is currently in progress.
     */
    object Loading : AuthResult<Nothing>()
}
