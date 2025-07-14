package com.example.orb_ed.presentation.screens.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.VerifyOtpUseCase
import com.example.orb_ed.domain.usecase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpState())
    val uiState: StateFlow<OtpState> = _uiState.asStateFlow()

    private val _effect = kotlinx.coroutines.channels.Channel<OtpEffect>()
    val effect = _effect.receiveAsFlow()

    private var countdownJob: Job? = null
    private var remainingTimeInSeconds = 300 // 5 minutes in seconds

    init {
        startCountdown()
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        remainingTimeInSeconds = 300 // Reset to 5 minutes
        updateTimerText()
        
        countdownJob = viewModelScope.launch {
            while (remainingTimeInSeconds > 0) {
                delay(1000)
                remainingTimeInSeconds--
                updateTimerText()
            }
            // Enable resend when countdown finishes
            _uiState.value = _uiState.value.copy(isResendEnabled = true)
        }
    }

    private fun updateTimerText() {
        val minutes = remainingTimeInSeconds / 60
        val seconds = remainingTimeInSeconds % 60
        _uiState.value = _uiState.value.copy(
            timeRemaining = String.format("%02d:%02d", minutes, seconds)
        )
    }

    fun handleIntent(intent: OtpIntent) {
        when (intent) {
            is OtpIntent.OtpChanged -> updateOtp(intent.otp)
            OtpIntent.VerifyOtp -> verifyOtp()
            OtpIntent.ResendOtp -> resendOtp()
            OtpIntent.StartCountdown -> startCountdown()
            OtpIntent.UpdateTimer -> updateTimerText()
        }
    }

    private fun updateOtp(otp: String) {
        val filteredOtp = otp.filter { it.isDigit() }.take(6)
        _uiState.value = _uiState.value.copy(
            otp = filteredOtp,
            isOtpValid = filteredOtp.length == 6
        )
    }

    private fun verifyOtp() {
        val currentState = _uiState.value
        if (currentState.otp.length != 6) return

        viewModelScope.launch {
            _effect.send(OtpEffect.ShowLoading)
            
            when (val result = verifyOtpUseCase(currentState.email, currentState.otp)) {
                is AuthResult.Success -> {
                    _effect.send(OtpEffect.HideLoading)
                    _effect.send(OtpEffect.NavigateToNextScreen)
                }
                is AuthResult.Error -> {
                    _effect.send(OtpEffect.HideLoading)
                    _effect.send(OtpEffect.ShowError(result.errorMessage))
                }
                is AuthResult.Loading -> {
                    // Loading state is handled by ShowLoading effect
                }
            }
        }
    }

    private fun resendOtp() {
        if (!_uiState.value.isResendEnabled) return
        
        viewModelScope.launch {
            // In a real app, this would call the API to resend OTP
            // For now, we'll just reset the timer
            startCountdown()
            _uiState.value = _uiState.value.copy(isResendEnabled = false)
            _effect.send(OtpEffect.ShowResendSuccess("OTP resent successfully"))
        }
    }

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
