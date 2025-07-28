package com.example.orb_ed.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.manager.UserManager
import com.example.orb_ed.data.remote.api.ApiService
import com.example.orb_ed.data.remote.interceptor.AuthInterceptor
import com.example.orb_ed.data.repository.AuthRepositoryImpl
import com.example.orb_ed.domain.repository.AuthRepository
import com.example.orb_ed.domain.usecase.auth.LoginUseCase
import com.example.orb_ed.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides application-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the AuthInterceptor for adding authentication tokens to requests.
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    /**
     * Provides the AuthRepository implementation.
     */
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        tokenManager: TokenManager,
        userManager: UserManager,
        networkUtils: NetworkUtils
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, tokenManager, userManager, networkUtils)
    }

    /**
     * Provides the LoginUseCase for handling user login.
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    /**
     * Provides the NetworkUtils for network-related operations.
     */
    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
    @Provides
    @Singleton
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): EncryptedSharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "secure_auth_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }
}
