package com.example.orb_ed.data.remote.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing a login request.
 *
 * @property emailAddress The user's email address.
 * @property password The user's password.
 * @property tenantContext The tenant context (hardcoded to "1").
 */
@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email_address")
    val emailAddress: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "tenant_context")
    val tenantContext: String = "1" // Hardcoded as per requirement
)
