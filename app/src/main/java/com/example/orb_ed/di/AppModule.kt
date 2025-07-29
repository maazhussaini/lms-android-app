package com.example.orb_ed.di

import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
     * Provides the LoginUseCase for handling user login.
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: com.example.orb_ed.domain.repository.AuthRepository): com.example.orb_ed.domain.usecase.auth.LoginUseCase {
        return com.example.orb_ed.domain.usecase.auth.LoginUseCase(authRepository)
    }
}
