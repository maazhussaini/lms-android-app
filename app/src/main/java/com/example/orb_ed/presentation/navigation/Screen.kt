package com.example.orb_ed.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed class representing all screens in the app with type-safe navigation.
 * Each screen is marked as @Serializable for type-safe navigation.
 */

// Screens without arguments
@Serializable
object Splash

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object ForgotPassword

@Serializable
object Home

@Serializable
object ResetPassword

// Screens with arguments
@Serializable
data class OTP(val email: String)
