package com.example.orb_ed.data.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("success")
    val success: Boolean,

    @SerialName("statusCode")
    val statusCode: Int,

    @SerialName("message")
    val message: String,

    @SerialName("timestamp")
    val timestamp: String,

    @SerialName("errorCode")
    val errorCode: String,

    @SerialName("correlationId")
    val correlationId: String,

    @SerialName("path")
    val path: String
)
