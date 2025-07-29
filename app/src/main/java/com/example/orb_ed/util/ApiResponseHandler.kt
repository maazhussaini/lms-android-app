package com.example.orb_ed.util

import com.example.orb_ed.domain.usecase.auth.AuthResult
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

/**
 * Handles API responses and converts them to a standardized result format.
 */
object ApiResponseHandler {

    /**
     * Handles a generic API call and returns a [Result] object.
     *
     * @param apiCall The suspend function that makes the API call.
     * @return A [Result] object containing either the success data or an error message.
     */
    suspend fun <T> handleApiCall(
        apiCall: suspend () -> T
    ): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Unauthorized. Please log in again."
                403 -> "Access denied. You don't have permission to perform this action."
                404 -> "The requested resource was not found."
                500 -> "An internal server error occurred. Please try again later."
                else -> "An error occurred: ${e.message()}"
            }
            Result.failure(Exception(errorMessage))
        } catch (e: IOException) {
            Result.failure(Exception("Network error. Please check your internet connection."))
        } catch (e: SerializationException) {
            Result.failure(Exception("Error parsing server response. Please try again."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Converts a [Result] to an [AuthResult].
     *
     * @param onSuccess A lambda that transforms the success data to the desired type.
     * @return An [AuthResult] representing the result of the operation.
     */
    /**
     * Maps a [Result] to an [AuthResult], handling success and error cases.
     *
     * @param onSuccess A lambda that transforms the success data to the desired type.
     * @return An [AuthResult] representing the result of the operation.
     */
    /*fun <T, R> Result<T>.toAuthResult(
        onSuccess: (T) -> R
    ): AuthResult<R> {
        return when (val result = this) {
            is Result.Success -> {
                try {
                    val data = result.data
                    if (data != null) {
                        AuthResult.Success(onSuccess(data))
                    } else {
                        AuthResult.Error("Response data is null")
                    }
                } catch (e: Exception) {
                    AuthResult.Error("Failed to process response: ${e.message ?: "Unknown error"}")
                }
            }
            is Result.Failure -> {
                val errorMessage = result.message.ifEmpty { "An unknown error occurred" }
                AuthResult.Error(errorMessage)
            }
            is Result.Exception -> {
                val errorMessage = result.e.message ?: "An unexpected error occurred"
                when (result.e) {
                    is kotlinx.serialization.SerializationException -> 
                        AuthResult.Error("Data serialization error: $errorMessage")
                    is java.net.ConnectException -> 
                        AuthResult.Error("Unable to connect to the server. Please check your internet connection.")
                    is java.net.SocketTimeoutException -> 
                        AuthResult.Error("Connection timed out. Please try again.")
                    is java.net.UnknownHostException -> 
                        AuthResult.Error("Unable to resolve host. Please check your internet connection.")
                    else -> AuthResult.Error("An error occurred: $errorMessage")
                }
            }
        }
    }*/
}
