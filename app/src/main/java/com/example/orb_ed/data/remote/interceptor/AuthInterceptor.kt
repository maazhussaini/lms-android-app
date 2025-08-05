package com.example.orb_ed.data.remote.interceptor

import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.util.AuthEvent
import com.example.orb_ed.util.AuthEventBus
import com.example.orb_ed.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject

/**
 * Interceptor that adds an authentication token to requests and handles token refresh logic.
 * Skips authentication for whitelisted endpoints.
 *
 * @property tokenManager Manages authentication tokens.
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    /**
     * Intercepts the request to add the authentication token.
     *
     * @param chain The interceptor chain.
     * @return The response from the server.
     * @throws IOException If an I/O error occurs.
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        // Skip auth endpoints and other public endpoints
        if (isPublicEndpoint(path)) {
            return chain.proceed(request)
        }

        // Get the current access token
        val accessToken = tokenManager.accessToken

        // If no token is available, proceed without it (will likely result in a 401)
        if (accessToken.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        // Add the authorization header
        val authenticatedRequest = request.newBuilder()
            .header(Constants.HEADER_AUTHORIZATION, "${Constants.TOKEN_TYPE_BEARER} $accessToken")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        // Execute the request
        val response = chain.proceed(authenticatedRequest)

        // If we get a 401, the token might be expired
        if (response.code == 401) {
            response.close()

            // Try to refresh the token
            val newToken = runBlocking {
                try {
                    tokenManager.refreshToken()
                } catch (e: Exception) {
                    null
                }
            }

            // If refresh failed, post authentication failed event
            if (newToken == null) {
                runBlocking {
                    AuthEventBus.postEvent(
                        AuthEvent.AuthenticationFailed("Authentication failed - Please log in again")
                    )
                }
                throw HttpException("Authentication failed - Please log in again")
            }

            // Retry the original request with the new token
            val newRequest = request.newBuilder()
                .header(Constants.HEADER_AUTHORIZATION, "${Constants.TOKEN_TYPE_BEARER} $newToken")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    /**
     * Checks if the requested endpoint is public and doesn't require authentication.
     *
     * @param path The request path.
     * @return `true` if the endpoint is public, `false` otherwise.
     */
    private fun isPublicEndpoint(path: String): Boolean {
        return Constants.PUBLIC_ENDPOINTS.any { endpoint ->
            path.equals(endpoint, ignoreCase = true) ||
                    path.endsWith("/$endpoint", ignoreCase = true)
        }
    }

    /**
     * Custom exception for HTTP errors.
     */
    class HttpException(override val message: String) : IOException(message)
}
