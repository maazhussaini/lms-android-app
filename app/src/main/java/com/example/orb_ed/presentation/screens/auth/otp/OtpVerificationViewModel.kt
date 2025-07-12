package com.example.orb_ed.presentation.screens.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<OtpVerificationUiState>(OtpVerificationUiState.Idle)
    val uiState: StateFlow<OtpVerificationUiState> = _uiState.asStateFlow()
    
    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _uiState.value = OtpVerificationUiState.Loading
            when (val result = verifyOtpUseCase(email, otp)) {
                is AuthResult.Success -> {
                    _uiState.value = OtpVerificationUiState.Success
                }
                is AuthResult.Error -> {
                    _uiState.value = OtpVerificationUiState.Error(result.errorMessage)
                }
                is AuthResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }
    
    // For demo purposes, this would call the API to resend OTP
    fun resendOtp(email: String) {
        viewModelScope.launch {
            // In a real app, this would call the API to resend OTP
            // For now, we'll just simulate a successful resend
            _uiState.value = OtpVerificationUiState.Idle
        }
    }
}

sealed class OtpVerificationUiState {
    object Idle : OtpVerificationUiState()
    object Loading : OtpVerificationUiState()
    object Success : OtpVerificationUiState()
    data class Error(val message: String) : OtpVerificationUiState()
}
