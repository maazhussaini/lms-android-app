package com.example.orb_ed.data.remote.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a login response.
 *
 * @property success Indicates if the login was successful.
 * @property message A message describing the result of the login attempt.
 * @property data The login response data containing user information and tokens.
 */
@Serializable
data class LoginResponse(
    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: LoginData? = null,

    @SerialName("timestamp")
    val timestamp: String,

    @SerialName("correlationId")
    val correlationId: String
)


/**
 * Data class representing the login response data.
 *
 * @property user The user information.
 * @property tokens The authentication tokens.
 */
@Serializable
data class LoginData(
    @SerialName("user")
    val user: User? = null,

    @SerialName("tokens")
    val tokens: Tokens? = null,

    @SerialName("permissions")
    val permissions: List<String>? = null
)

/**
 * Data class representing user information.
 *
 * @property id The user's unique identifier.
 * @property email The user's email address.
 * @property name The user's full name.
 * @property role The user's role.
 */
@Serializable
data class Role(
    @SerialName("role_name")
    val roleName: String
)

@Serializable
data class User(
    @SerialName("id")
    val id: Long,

    @SerialName("username")
    val username: String,

    @SerialName("full_name")
    val fullName: String,

    @SerialName("email")
    val email: String,

    @SerialName("role")
    val role: Role,

    @SerialName("tenant_id")
    val tenantId: Long,

    @SerialName("user_type")
    val userType: String
)

/**
 * Data class representing authentication tokens.
 *
 * @property accessToken The access token for API authentication.
 * @property refreshToken The refresh token for obtaining a new access token.
 * @property expiresIn The number of seconds until the access token expires.
 * @property tokenType The type of token.
 */
@Serializable
data class Tokens(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("expires_in")
    val expiresIn: Long,

    @SerialName("token_type")
    val tokenType: String = "Bearer"
)
