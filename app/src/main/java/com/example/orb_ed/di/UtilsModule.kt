package com.example.orb_ed.di

import android.content.Context
import com.example.orb_ed.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides utility-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    /**
     * Provides the NetworkUtils for network-related operations.
     */
    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
}
