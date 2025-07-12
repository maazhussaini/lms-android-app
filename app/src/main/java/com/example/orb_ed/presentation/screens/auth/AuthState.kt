package com.example.orb_ed.presentation.screens.auth

import com.example.orb_ed.presentation.base.UiEffect
import com.example.orb_ed.presentation.base.UiEvent
import com.example.orb_ed.presentation.base.UiState

/**
 * Represents the different states for the authentication flow.
 */
sealed class AuthState : UiState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

/**
 * Represents the different events that can be triggered from the UI for authentication.
 */
sealed class AuthEvent : UiEvent {
    // Login Events
    data class Login(val email: String, val password: String) : AuthEvent()
    
    // Sign Up Events
    data class SignUp(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : AuthEvent()
    
    // Forgot Password Events
    data class ForgotPassword(val email: String) : AuthEvent()
    
    // OTP Verification Events
    data class VerifyOTP(val email: String, val otp: String) : AuthEvent()
    
    // Navigation Events
    object NavigateToLogin : AuthEvent()
    object NavigateToSignUp : AuthEvent()
    object NavigateToForgotPassword : AuthEvent()
    object NavigateBack : AuthEvent()
}

/**
 * Represents the different side effects that can be triggered from the ViewModel for authentication.
 */
sealed class AuthEffect : UiEffect {
    // Navigation Effects
    object NavigateToHome : AuthEffect()
    data class NavigateToOTP(val email: String) : AuthEffect()
    object NavigateBack : AuthEffect()
    
    // UI Effects
    data class ShowError(val message: String) : AuthEffect()
    data class ShowMessage(val message: String) : AuthEffect()
}
