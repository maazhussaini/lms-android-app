package com.example.orb_ed.di

import com.example.orb_ed.BuildConfig
import com.example.orb_ed.data.remote.api.ApiService
import com.example.orb_ed.data.remote.api.CourseApiService
import com.example.orb_ed.data.remote.interceptor.AuthInterceptor
import com.example.orb_ed.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIMEOUT_SECONDS = 30L
    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    /**
     * Provides a JSON instance for Kotlin Serialization.
     *
     * Configuration:
     * - ignoreUnknownKeys: Skip unknown JSON fields instead of throwing SerializationException
     * - isLenient: Allow non-strict JSON format
     * - prettyPrint: Format JSON with indentation for better readability
     * - explicitNulls: Include null values in serialization
     * - coerceInputValues: Coerce invalid values to default values when possible
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        explicitNulls = false
        coerceInputValues = true
    }

    /**
     * Provides an HTTP logging interceptor for debugging purposes.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    /**
     * Provides an OkHttpClient with interceptors and timeouts configured.
     *
     * Configuration includes:
     * - Logging interceptor for debugging
     * - Auth interceptor for token injection
     * - Timeout settings
     * - Connection settings
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // Add logging interceptor (should be last)
            .addInterceptor(loggingInterceptor)
            // Add auth interceptor for token injection
            .addInterceptor(authInterceptor)
            // Configure timeouts
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            // Connection settings
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    /**
     * Provides a Retrofit instance with Gson converter and base URL.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    /**
     * Provides the API service interface for making network requests.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides the Course API service interface for making course-related network requests.
     */
    @Provides
    @Singleton
    fun provideCourseApiService(retrofit: Retrofit): CourseApiService {
        return retrofit.create(CourseApiService::class.java)
    }
}
