package com.example.orb_ed.data.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.orb_ed.util.Constants
import timber.log.Timber
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
            Timber.e(e, "Failed to create master key")
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
            Timber.e(e, "Security exception while creating EncryptedSharedPreferences")
            throw e
        } catch (e: IOException) {
            Timber.e(e, "IO exception while creating EncryptedSharedPreferences")
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
            Timber.e(e, "Error retrieving access token")
            null
        }

    /**
     * The current refresh token, or null if not available.
     */
    val refreshToken: String?
        get() = try {
            sharedPreferences.getString(Constants.KEY_REFRESH_TOKEN, null)
        } catch (e: Exception) {
            Timber.e(e, "Error retrieving refresh token")
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
    fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Int) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        // Convert expiresIn (seconds) to expiry timestamp (millis)
        this.tokenExpiry = System.currentTimeMillis() + (expiresIn * 1000L)
    }

    /**
     * Clears all stored tokens.
     */
    fun clearTokens() {
        prefs.edit().apply {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_TOKEN_EXPIRY)
            apply()
        }
    }

    /**
     * Checks if the current access token is expired or about to expire.
     *
     * @return True if the token is expired or will expire within the buffer period, false otherwise.
     */
    fun isTokenExpired(): Boolean {
        return accessToken.isNullOrEmpty() ||
                System.currentTimeMillis() >= (tokenExpiry - TOKEN_EXPIRY_BUFFER_MS)
    }
}
