package com.example.orb_ed.di

import com.example.orb_ed.data.manager.TokenManager
import com.example.orb_ed.data.remote.api.CourseApiService
import com.example.orb_ed.data.repository.AuthRepositoryImpl
import com.example.orb_ed.data.repository.CourseRepositoryImpl
import com.example.orb_ed.domain.repository.AuthRepository
import com.example.orb_ed.domain.repository.CourseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Provides the [AuthRepository] implementation.
     *
     * @param authRepositoryImpl The implementation of [AuthRepository].
     * @return The [AuthRepository] instance.
     */
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        /**
         * Provides the [CourseRepository] implementation.
         *
         * @param api The [CourseApiService] for making network requests.
         * @param tokenManager The [TokenManager] for handling authentication tokens.
         * @return The [CourseRepository] instance.
         */
        @Provides
        @Singleton
        fun provideCourseRepository(
            api: CourseApiService,
            tokenManager: TokenManager
        ): CourseRepository {
            return CourseRepositoryImpl(api, tokenManager)
        }
    }
}
