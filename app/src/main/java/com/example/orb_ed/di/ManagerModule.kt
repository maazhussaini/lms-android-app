package com.example.orb_ed.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.manager.UserManager
import com.example.orb_ed.util.Constants
import com.example.orb_ed.util.NetworkUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides manager-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    /**
     * Provides a MasterKey for secure storage.
     */
    @Provides
    @Singleton
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey {
        return MasterKey.Builder(context, Constants.PREFERENCES_NAME)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    /**
     * Provides an instance of EncryptedSharedPreferences for secure storage.
     */
    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): EncryptedSharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "${Constants.PREFERENCES_NAME}_encrypted",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    /**
     * Provides the TokenManager for managing authentication tokens.
     */
    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): TokenManager = TokenManager(context)

    /**
     * Provides the UserManager for managing user data.
     */
    @Provides
    @Singleton
    fun provideUserManager(
        @ApplicationContext context: Context,
        gson: Gson
    ): UserManager = UserManager(context, gson)

    /**
     * Provides the NetworkUtils for network-related operations.
     */
    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
}
