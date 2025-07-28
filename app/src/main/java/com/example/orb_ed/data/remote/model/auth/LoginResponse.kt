package com.example.orb_ed.data.remote.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing a login response.
 *
 * @property success Indicates if the login was successful.
 * @property message A message describing the result of the login attempt.
 * @property data The login response data containing user information and tokens.
 */
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "success")
    val success: Boolean,

    @Json(name = "message")
    val message: String,

    @Json(name = "data")
    val data: LoginData? = null
)

/**
 * Data class representing the login response data.
 *
 * @property user The user information.
 * @property tokens The authentication tokens.
 */
@JsonClass(generateAdapter = true)
data class LoginData(
    @Json(name = "user")
    val user: User? = null,

    @Json(name = "tokens")
    val tokens: Tokens? = null
)

/**
 * Data class representing user information.
 *
 * @property id The user's unique identifier.
 * @property email The user's email address.
 * @property name The user's full name.
 * @property role The user's role.
 */
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "role")
    val role: String
)

/**
 * Data class representing authentication tokens.
 *
 * @property accessToken The access token for API authentication.
 * @property refreshToken The refresh token for obtaining a new access token.
 * @property expiresIn The number of seconds until the access token expires.
 * @property tokenType The type of token.
 */
@JsonClass(generateAdapter = true)
data class Tokens(
    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "refresh_token")
    val refreshToken: String,

    @Json(name = "expires_in")
    val expiresIn: Int? = null,

    @Json(name = "token_type")
    val tokenType: String
)
