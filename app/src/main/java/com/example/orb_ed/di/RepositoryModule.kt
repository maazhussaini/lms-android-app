package com.example.orb_ed.di

import com.example.orb_ed.data.repository.AuthRepositoryImpl
import com.example.orb_ed.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
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
}
