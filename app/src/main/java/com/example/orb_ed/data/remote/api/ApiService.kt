package com.example.orb_ed.data.remote.api

import com.example.orb_ed.data.remote.model.auth.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Defines the API endpoints for the application.
 * Define your API endpoints here.
 */
interface ApiService {
    @POST(LOGIN_ENDPOINT)
    @Headers("$HEADER_CONTENT_TYPE: $CONTENT_TYPE_JSON")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

/**
 * Wrapper class for handling API responses.
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

/**
 * Extension function to handle API responses in a more concise way
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                ApiResult.Success(body)
            } ?: ApiResult.Error("Empty response body")
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Unknown error occurred"
            ApiResult.Error(errorMsg, response.code())
        }
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Network call failed")
    }
}
