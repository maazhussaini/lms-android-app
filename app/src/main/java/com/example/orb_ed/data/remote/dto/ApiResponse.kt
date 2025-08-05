package com.example.orb_ed.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: T,
    val timestamp: String,
    val correlationId: String
)
