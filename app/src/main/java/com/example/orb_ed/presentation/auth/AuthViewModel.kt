package com.example.orb_ed.presentation.auth

import com.example.orb_ed.data.manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : androidx.lifecycle.ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Authenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun onAuthenticationFailed() {
        _authState.value = AuthState.Unauthenticated("Authentication failed - Please log in again")
    }

    fun clearAuthState() {
        _authState.value = AuthState.Authenticated
        tokenManager.clearTokens()
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    data class Unauthenticated(val message: String) : AuthState()
}
