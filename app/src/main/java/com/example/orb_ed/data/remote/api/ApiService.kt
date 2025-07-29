package com.example.orb_ed.data.remote.api

import com.example.orb_ed.data.remote.model.auth.LoginRequest
import com.example.orb_ed.data.remote.model.auth.LoginResponse
import com.example.orb_ed.util.Constants
import com.example.orb_ed.util.Constants.Endpoints
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Defines the API endpoints for the application.
 * Define your API endpoints here.
 */
interface ApiService {
    // Authentication Endpoints
    /**
     * Authenticate a user with email and password
     * @param request The login request containing email and password
     * @return A response containing the authentication result
     */
    @POST(Endpoints.LOGIN)
    @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    /**
     * Register a new user
     * @param request The signup request containing user details
     * @return A response containing the registration result
     */
    /* @POST(Endpoints.SIGNUP)
     @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
     suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

     @POST(Endpoints.FORGOT_PASSWORD)
     @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
     suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

     @POST("auth/verify-otp")
     @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
     suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

     @POST("auth/reset-password")
     @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
     suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>

     @POST(Endpoints.REFRESH_TOKEN)
     @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
     suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

     // User Endpoints
     @GET(Endpoints.USER_PROFILE)
     suspend fun getUserProfile(): Response<UserProfileResponse>

     */
    /**
     * Update user profile information
     * @param request The update profile request containing updated user details
     * @return A response containing the updated profile information
     *//*
    @PUT(Endpoints.UPDATE_PROFILE)
    @Headers("${Constants.HEADER_CONTENT_TYPE}: ${Constants.CONTENT_TYPE_JSON}")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>*/

}

/**
 * Wrapper class for handling API responses.
 */
@Serializable
sealed class ApiResult<out T> {
    @Serializable
    data class Success<out T>(val data: T) : ApiResult<T>()

    @Serializable
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()

    @Serializable
    object Loading : ApiResult<Nothing>()

    /**
     * Helper to get the success data or null if not a success.
     */
    fun getOrNull(): T? = (this as? Success)?.data

    /**
     * Helper to get the error message or null if not an error.
     */
    fun errorMessageOrNull(): String? = (this as? Error)?.message
}

/**
 * Extension function to handle API responses in a more concise way with better error handling.
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResult.Success(it)
            } ?: ApiResult.Error(
                message = "Empty response body",
                code = response.code()
            )
        } else {
            val errorBody = response.errorBody()?.string()
            ApiResult.Error(
                message = "API error: ${response.code()} - ${response.message()}. $errorBody",
                code = response.code()
            )
        }
    } catch (e: Exception) {
        ApiResult.Error(
            message = e.message ?: "An unknown error occurred",
            code = (e as? retrofit2.HttpException)?.code()
        )
    }
}
