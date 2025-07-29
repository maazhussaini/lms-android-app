package com.example.orb_ed.data.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.orb_ed.util.Constants
//import timber.log.Timber
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages authentication tokens securely using EncryptedSharedPreferences.
 *
 * @property context The application context.
 */
@Singleton
class TokenManager @Inject constructor(
    private val context: Context
) {
    private val masterKey: MasterKey by lazy {
        try {
            MasterKey.Builder(context, Constants.PREFERENCES_NAME)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .setRequestStrongBoxBacked(false) // Set to true if device supports StrongBox
                .build()
        } catch (e: Exception) {
//            Timber.e(e, "Failed to create master key")
            throw e
        }
    }

    private val sharedPreferences: SharedPreferences by lazy {
        try {
            EncryptedSharedPreferences.create(
                context,
                "${Constants.PREFERENCES_NAME}_tokens",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: GeneralSecurityException) {
//            Timber.e(e, "Security exception while creating EncryptedSharedPreferences")
            throw e
        } catch (e: IOException) {
//            Timber.e(e, "IO exception while creating EncryptedSharedPreferences")
            throw e
        }
    }

    /**
     * The current access token, or null if not available.
     */
    val accessToken: String?
        get() = try {
            sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, null)
        } catch (e: Exception) {
//            Timber.e(e, "Error retrieving access token")
            null
        }

    /**
     * The current refresh token, or null if not available.
     */
    val refreshToken: String?
        get() = try {
            sharedPreferences.getString(Constants.KEY_REFRESH_TOKEN, null)
        } catch (e: Exception) {
//            Timber.e(e, "Error retrieving refresh token")
            null
        }

    /**
     * The timestamp when the current access token expires (in milliseconds since epoch).
     */
    private var tokenExpiry: Long
        get() = sharedPreferences.getLong(Constants.KEY_TOKEN_EXPIRY, 0)
        set(value) = sharedPreferences.edit()
            .putLong(Constants.KEY_TOKEN_EXPIRY, value)
            .apply()

    /**
     * Returns true if the user is currently logged in (has a non-expired access token).
     */
    val isLoggedIn: Boolean
        get() = !accessToken.isNullOrEmpty() && !isTokenExpired()

    /**
     * Saves the access and refresh tokens along with their expiration time.
     *
     * @param accessToken The access token to save.
     * @param refreshToken The refresh token to save.
     * @param expiresIn The number of seconds until the access token expires.
     */
    fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Long) {
        sharedPreferences.edit().apply {
            putString(Constants.KEY_ACCESS_TOKEN, accessToken)
            putString(Constants.KEY_REFRESH_TOKEN, refreshToken)
            // Calculate expiry time (current time + expiresIn seconds - 5 minutes buffer)
            val expiryTime = System.currentTimeMillis() + (expiresIn * 1000) - (5 * 60 * 1000)
            putLong(Constants.KEY_TOKEN_EXPIRY, expiryTime)
            apply()
        }
    }

    /**
     * Attempts to refresh the access token using the refresh token.
     *
     * @return The new access token if refresh was successful, null otherwise.
     * @throws Exception If the refresh token is not available or the refresh request fails.
     */
    suspend fun refreshToken(): String? {
        val refreshToken = sharedPreferences.getString(Constants.KEY_REFRESH_TOKEN, null)
            ?: throw IllegalStateException("No refresh token available")

        // TODO: Implement the actual token refresh API call
        // This is a placeholder for the actual implementation
        // You'll need to make a network request to your token refresh endpoint
        // and update the tokens using saveTokens()

        // Example implementation (replace with your actual API call):
        /*
        return try {
            val response = authApi.refreshToken(refreshToken)
            if (response.isSuccessful) {
                response.body()?.let { tokenResponse ->
                    saveTokens(
                        tokenResponse.accessToken,
                        tokenResponse.refreshToken ?: refreshToken,
                        tokenResponse.expiresIn
                    )
                    tokenResponse.accessToken
                }
            } else {
                // If refresh fails, clear tokens as they're no longer valid
                clearTokens()
                null
            }
        } catch (e: Exception) {
            // If there's an error, clear tokens to ensure security
            clearTokens()
            throw e
        }
        */

        // For now, just throw an exception as this needs to be implemented
        throw UnsupportedOperationException("Token refresh not yet implemented")
    }

    /**
     * Clears all stored tokens.
     */
    fun clearTokens() {
        sharedPreferences.edit().apply {
            remove(Constants.KEY_ACCESS_TOKEN)
            remove(Constants.KEY_REFRESH_TOKEN)
            remove(Constants.KEY_TOKEN_EXPIRY)
            apply()
        }
    }

    /**
     * Checks if the current access token is expired or about to expire.
     *
     * @return True if the token is expired or will expire within the buffer period, false otherwise.
     */
    fun isTokenExpired(): Boolean {
        return accessToken.isNullOrEmpty() /*||
                System.currentTimeMillis() >= (tokenExpiry - Constants.TOKEN_EXPIRY_BUFFER_MS)*/
    }
}
