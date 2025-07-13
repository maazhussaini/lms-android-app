package com.example.orb_ed.presentation.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _intent = Channel<LoginIntent>(Channel.BUFFERED)
    private val intent = _intent.receiveAsFlow()

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is LoginIntent.EmailChanged -> {
                        _uiState.value = _uiState.value.copy(email = intent.email)
                    }
                    is LoginIntent.PasswordChanged -> {
                        _uiState.value = _uiState.value.copy(password = intent.password)
                    }
                    LoginIntent.TogglePasswordVisibility -> {
                        _uiState.value = _uiState.value.copy(
                            isPasswordVisible = !_uiState.value.isPasswordVisible
                        )
                    }
                    LoginIntent.Login -> {
                        val currentState = _uiState.value
                        viewModelScope.launch {
                            _effect.send(LoginEffect.ShowLoading)
                            when (val result = loginUseCase(
                                email = currentState.email,
                                password = currentState.password
                            )) {
                                is AuthResult.Success -> {
                                    _effect.send(LoginEffect.HideLoading)
                                    _effect.send(LoginEffect.NavigateToNextScreen())
                                }
                                is AuthResult.Error -> {
                                    _effect.send(LoginEffect.HideLoading)
                                    _effect.send(LoginEffect.ShowError(result.errorMessage))
                                }
                                is AuthResult.Loading -> {
                                    // Handle loading state if needed
                                }
                            }
                        }
                    }
                    LoginIntent.NavigateToSignup -> {
                        _effect.send(LoginEffect.NavigateToSignup)
                    }
                    LoginIntent.NavigateToForgotPassword -> {
                        _effect.send(LoginEffect.NavigateToForgotPassword)
                    }
                }
            }
        }
    }

    fun sendIntent(intent: LoginIntent) {
        viewModelScope.launch {
            _intent.send(intent)
        }
    }
}
