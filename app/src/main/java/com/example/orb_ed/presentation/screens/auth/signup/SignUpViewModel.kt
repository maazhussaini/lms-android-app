package com.example.orb_ed.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    
    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        // Basic validation
        if (password != confirmPassword) {
            _uiState.value = SignUpUiState.Error("Passwords do not match")
            return
        }
        
        if (password.length < 6) {
            _uiState.value = SignUpUiState.Error("Password must be at least 6 characters")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading
            when (val result = signUpUseCase(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )) {
                is AuthResult.Success -> {
                    _uiState.value = SignUpUiState.Success
                }
                is AuthResult.Error -> {
                    _uiState.value = SignUpUiState.Error(result.errorMessage)
                }
                is AuthResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }
}

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    object Success : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}
