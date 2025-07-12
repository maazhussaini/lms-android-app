package com.example.orb_ed.presentation.screens.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()
    
    fun sendResetLink(email: String) {
        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            when (val result = forgotPasswordUseCase(email)) {
                is AuthResult.Success -> {
                    _uiState.value = ForgotPasswordUiState.Success
                }
                is AuthResult.Error -> {
                    _uiState.value = ForgotPasswordUiState.Error(result.errorMessage)
                }
                is AuthResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    object Success : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
