package com.example.orb_ed.presentation.screens.auth.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.ResetPasswordUseCase
import com.example.orb_ed.domain.usecase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResetPasswordState())
    val uiState: StateFlow<ResetPasswordState> = _uiState.asStateFlow()

    private val _effect = Channel<ResetPasswordEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _intent = Channel<ResetPasswordIntent>(Channel.BUFFERED)
    private val intent = _intent.receiveAsFlow()

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is ResetPasswordIntent.PasswordChanged -> {
                        _uiState.value = _uiState.value.copy(password = intent.password)
                    }
                    is ResetPasswordIntent.ConfirmPasswordChanged -> {
                        _uiState.value = _uiState.value.copy(confirmPassword = intent.confirmPassword)
                    }
                    ResetPasswordIntent.TogglePasswordVisibility -> {
                        _uiState.value = _uiState.value.copy(
                            isPasswordVisible = !_uiState.value.isPasswordVisible
                        )
                    }
                    ResetPasswordIntent.ToggleConfirmPasswordVisibility -> {
                        _uiState.value = _uiState.value.copy(
                            isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible
                        )
                    }
                    ResetPasswordIntent.ResetPassword -> {
                        resetPassword()
                    }
                    ResetPasswordIntent.NavigateToLogin -> {
                        _effect.send(ResetPasswordEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            _effect.send(ResetPasswordEffect.ShowLoading)
            
            val currentState = _uiState.value
            
            when (val result = resetPasswordUseCase(
                password = currentState.password,
                confirmPassword = currentState.confirmPassword
            )) {
                is AuthResult.Success -> {
                    _effect.send(ResetPasswordEffect.HideLoading)
                    _effect.send(ResetPasswordEffect.ShowSuccess("Password reset successfully"))
                    _effect.send(ResetPasswordEffect.NavigateToLogin)
                }
                is AuthResult.Error -> {
                    _effect.send(ResetPasswordEffect.HideLoading)
                    _effect.send(ResetPasswordEffect.ShowError(result.errorMessage))
                }
                is AuthResult.Loading -> {
                    // Loading state is already handled by ShowLoading effect
                }
            }
        }
    }

    fun sendIntent(intent: ResetPasswordIntent) {
        viewModelScope.launch {
            _intent.send(intent)
        }
    }
}
