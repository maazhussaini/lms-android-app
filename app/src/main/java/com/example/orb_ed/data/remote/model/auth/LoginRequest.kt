package com.example.orb_ed.data.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a login request.
 *
 * @property emailAddress The user's email address.
 * @property password The user's password.
 * @property tenantContext The tenant context (hardcoded to "1").
 */
@Serializable
data class LoginRequest(
    @SerialName("email_address")
    val emailAddress: String,

    @SerialName("password")
    val password: String,

    @SerialName("tenant_context")
    val tenantContext: String = "1" // Hardcoded as per requirement
)
