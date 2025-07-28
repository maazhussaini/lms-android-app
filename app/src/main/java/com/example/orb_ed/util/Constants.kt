package com.example.orb_ed.util

/**
 * Application-wide constants.
 */
object Constants {
    // API Configuration
    const val BASE_URL = "https://skonzify.com/api/v1/"
    const val API_TIMEOUT = 30L // seconds

    // Authentication
    const val TOKEN_TYPE_BEARER = "Bearer"
    const val TENANT_CONTEXT = "1" // Hardcoded tenant context

    // Shared Preferences
    const val PREFERENCES_NAME = "orb_ed_prefs"
    const val KEY_ACCESS_TOKEN = "access_token"
    const val KEY_REFRESH_TOKEN = "refresh_token"
    const val KEY_TOKEN_EXPIRY = "token_expiry"
    const val KEY_USER_DATA = "user_data"

    // HTTP Headers
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_ACCEPT = "Accept"
    const val CONTENT_TYPE_JSON = "application/json"

    // API Endpoints
    object Endpoints {
        // Auth endpoints
        const val LOGIN = "auth/student/login"
        const val SIGNUP = "auth/student/register"
        const val FORGOT_PASSWORD = "auth/forgot-password"
        const val REFRESH_TOKEN = "auth/refresh-token"

        // User endpoints
        const val USER_PROFILE = "user/profile"
        const val UPDATE_PROFILE = "user/update"

        // Add more endpoints as needed
    }

    // List of public endpoints that don't require authentication
    val PUBLIC_ENDPOINTS = listOf(
        Endpoints.LOGIN,
        Endpoints.SIGNUP,
        Endpoints.FORGOT_PASSWORD,
        "auth/" // All auth endpoints
    )
    // Database
    const val DATABASE_NAME = "orb_ed_database"
    
    // Timeouts
    const val CONNECT_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds
    const val WRITE_TIMEOUT = 30L // seconds
    
    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    
    // Other constants
    const val EMPTY_STRING = ""
}
