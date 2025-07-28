package com.example.orb_ed.data.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.orb_ed.data.remote.model.auth.User
import com.example.orb_ed.util.Constants
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages user data and authentication state using DataStore for persistence.
 *
 * @property context The application context.
 * @property gson Gson instance for JSON serialization/deserialization.
 */
@Singleton
class UserManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "${Constants.PREFERENCES_NAME}_user"
    )

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USER_ID = longPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("username")
        val FULL_NAME = stringPreferencesKey("full_name")
        val USER_ROLE = stringPreferencesKey("user_role")
        val TENANT_ID = stringPreferencesKey("tenant_id")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    companion object {
        private const val TAG = "UserManager"
    }

    /**
     * Flow of the current user data, or null if no user is logged in.
     */
    val userFlow: Flow<User?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading user data")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            try {
                val userId = preferences[PreferencesKeys.USER_ID] ?: return@map null
                val email = preferences[PreferencesKeys.USER_EMAIL] ?: return@map null
                val username = preferences[PreferencesKeys.USER_NAME] ?: return@map null
                val fullName = preferences[PreferencesKeys.FULL_NAME] ?: ""
                val role = preferences[PreferencesKeys.USER_ROLE] ?: ""
                val tenantId = preferences[PreferencesKeys.TENANT_ID]?.toIntOrNull() ?: 0

                User(
                    id = userId,
                    email = email,
                    username = username,
                    full_name = fullName,
                    role = User.Role(role, ""),
                    tenant_id = tenantId,
                    created_at = "",
                    updated_at = ""
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing user data", e)
                null
            }
        }
}

suspend fun saveUser(user: User) {
    dataStore.edit { preferences ->
        preferences[PreferencesKeys.USER_ID] = user.id.toLong()
        preferences[PreferencesKeys.USER_EMAIL] = user.email
        preferences[PreferencesKeys.USER_NAME] = user.username
        preferences[PreferencesKeys.FULL_NAME] = user.full_name
        preferences[PreferencesKeys.USER_ROLE] = user.role.role_name
        preferences[PreferencesKeys.TENANT_ID] = user.tenant_id.toString()
        preferences[PreferencesKeys.IS_LOGGED_IN] = true
    }
}

suspend fun clearUser() {
    dataStore.edit { preferences ->
        preferences.clear()
    }

    /**
     * Checks if a user is currently logged in.
     */
    val isLoggedIn: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading login state")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }
