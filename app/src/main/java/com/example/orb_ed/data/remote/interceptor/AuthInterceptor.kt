package com.example.orb_ed.data.remote.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            // Add your auth token here if needed
            // .header("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(newRequest)
    }
}
