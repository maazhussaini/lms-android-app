package com.example.orb_ed.data.remote.api

import retrofit2.http.GET

/**
 * Define your API endpoints here.
 */
interface ApiService {
    // Add your API endpoints here
}

/**
 * Wrapper class for handling API responses.
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>() // Optional: For tracking loading state
}
