package com.example.orb_ed.data.manager

//import timber.log.Timber
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.orb_ed.data.remote.model.auth.Role
import com.example.orb_ed.data.remote.model.auth.User
import com.example.orb_ed.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages user data and authentication state using DataStore for persistence.
 *
 * @property context The application context.
 * @property json Json instance for Kotlin serialization/deserialization.
 */
@Singleton
class UserManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "${Constants.PREFERENCES_NAME}_user"
    )

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USER_ID = longPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_USERNAME = stringPreferencesKey("user_username")
        val USER_FULL_NAME = stringPreferencesKey("user_full_name")
        val USER_ROLE = stringPreferencesKey("user_role")
        val USER_TENANT_ID = longPreferencesKey("user_tenant_id")
        val USER_TYPE = stringPreferencesKey("user_type")
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
//                Timber.e(exception, "Error reading user data")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            try {
                val userId = preferences[PreferencesKeys.USER_ID] ?: return@map null
                val email = preferences[PreferencesKeys.USER_EMAIL] ?: return@map null
                val username = preferences[PreferencesKeys.USER_USERNAME] ?: return@map null
                val fullName = preferences[PreferencesKeys.USER_FULL_NAME] ?: ""
                val role = Role(preferences[PreferencesKeys.USER_ROLE] ?: "")
                val tenantId = preferences[PreferencesKeys.USER_TENANT_ID] ?: 0L
                val userType = preferences[PreferencesKeys.USER_TYPE] ?: ""

                User(
                    id = userId,
                    email = email,
                    username = username,
                    fullName = fullName,
                    role = role,
                    tenantId = tenantId,
                    userType = userType
                )
            } catch (e: Exception) {
//                Timber.e(e, "Error parsing user data")
                null
            }
        }

    /**
     * Saves the user data to DataStore.
     *
     * @param user The user to save.
     */
    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = user.id
            preferences[PreferencesKeys.USER_EMAIL] = user.email
            preferences[PreferencesKeys.USER_USERNAME] = user.username
            preferences[PreferencesKeys.USER_FULL_NAME] = user.fullName
            preferences[PreferencesKeys.USER_ROLE] = user.role.roleName
            preferences[PreferencesKeys.USER_TENANT_ID] = user.tenantId
            preferences[PreferencesKeys.USER_TYPE] = user.userType
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
        }
    }

    /**
     * Clears all user data from DataStore.
     */
    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    /**
     * Flow indicating whether a user is currently logged in.
     */
    val isLoggedIn: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
//                Timber.e(exception, "Error reading login state")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }
}
