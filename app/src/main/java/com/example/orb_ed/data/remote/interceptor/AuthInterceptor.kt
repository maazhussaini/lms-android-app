package com.example.orb_ed.data.remote.interceptor

import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
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

        // Skip auth endpoints and other public endpoints
        if (isPublicEndpoint(request.url.encodedPath)) {
            return chain.proceed(request)
        }

        // Get the current access token
        val accessToken = tokenManager.accessToken

        // If no token is available, proceed without it (will likely result in a 401)
        if (accessToken == null) {
            return chain.proceed(request)
        }

        // Add the authorization header
        val authenticatedRequest = request.newBuilder()
            .header(Constants.HEADER_AUTHORIZATION, "${Constants.TOKEN_TYPE_BEARER} $accessToken")
            .build()

        // Execute the request
        val response = chain.proceed(authenticatedRequest)

        // If we get a 401, the token might be expired
        if (response.code == 401) {
            response.close()
            // TODO: Implement token refresh logic here
            throw IOException("Unauthorized - Token may have expired")
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
        return Constants.PUBLIC_ENDPOINTS.any { path.contains(it, ignoreCase = true) }
        return path.endsWith(LOGIN_ENDPOINT) ||
                path.endsWith(SIGNUP_ENDPOINT) ||
                path.endsWith(FORGOT_PASSWORD_ENDPOINT)
    }
}
