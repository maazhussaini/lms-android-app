package com.example.orb_ed.presentation.screens.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordState())
    val uiState: StateFlow<ForgotPasswordState> = _uiState.asStateFlow()

    private val _effect = Channel<ForgotPasswordEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _intent = Channel<ForgotPasswordIntent>(Channel.BUFFERED)
    private val intent = _intent.receiveAsFlow()

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is ForgotPasswordIntent.EmailChanged -> {
                        _uiState.value = _uiState.value.copy(email = intent.email)
                    }
                    ForgotPasswordIntent.SendOtp -> {
                        val currentState = _uiState.value
                        if (isValidEmail(currentState.email)) {
                            sendOtp(currentState.email)
                        } else {
                            _effect.send(ForgotPasswordEffect.ShowError("Please enter a valid email address"))
                        }
                    }
                    ForgotPasswordIntent.NavigateToLogin -> {
                        _effect.send(ForgotPasswordEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    private suspend fun sendOtp(email: String) {
        _effect.send(ForgotPasswordEffect.ShowLoading)
        when (val result = forgotPasswordUseCase(email)) {
            is AuthResult.Success -> {
                _effect.send(ForgotPasswordEffect.HideLoading)
                _effect.send(ForgotPasswordEffect.ShowSuccess("OTP sent to your email"))
            }
            is AuthResult.Error -> {
                _effect.send(ForgotPasswordEffect.HideLoading)
                _effect.send(ForgotPasswordEffect.ShowError(result.errorMessage))
            }
            is AuthResult.Loading -> {
                // Loading state is already handled by ShowLoading effect
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun sendIntent(intent: ForgotPasswordIntent) {
        viewModelScope.launch {
            _intent.send(intent)
        }
    }
}
