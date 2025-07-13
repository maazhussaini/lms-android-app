package com.example.orb_ed.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.usecase.auth.AuthResult
import com.example.orb_ed.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import android.util.Patterns
import com.example.orb_ed.presentation.screens.auth.signup.SignUpEffect.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _effect = Channel<SignUpEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _intent = Channel<SignUpIntent>(Channel.BUFFERED)
    private val intent = _intent.receiveAsFlow()

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is SignUpIntent.EmailChanged -> {
                        _uiState.value = _uiState.value.copy(email = intent.email)
                    }

                    is SignUpIntent.PhoneNumberChanged -> {
                        _uiState.value = _uiState.value.copy(phoneNumber = intent.phoneNumber)
                    }

                    is SignUpIntent.PasswordChanged -> {
                        _uiState.value = _uiState.value.copy(password = intent.password)
                    }

                    is SignUpIntent.ConfirmPasswordChanged -> {
                        _uiState.value =
                            _uiState.value.copy(confirmPassword = intent.confirmPassword)
                    }

                    is SignUpIntent.TogglePasswordVisibility -> {
                        _uiState.value = _uiState.value.copy(isPasswordVisible = intent.isVisible)
                    }

                    is SignUpIntent.ToggleConfirmPasswordVisibility -> {
                        _uiState.value =
                            _uiState.value.copy(isConfirmPasswordVisible = intent.isVisible)
                    }

                    SignUpIntent.SignUp -> {
                        val currentState = _uiState.value

                        viewModelScope.launch {
                            _effect.send(SignUpEffect.ShowLoading)
                            when (val result = signUpUseCase(
                                email = currentState.email,
                                password = currentState.password,
                                confirmPassword = currentState.confirmPassword,
                                phoneNumber = currentState.phoneNumber,
                                institution = if (currentState.isCheckboxChecked) currentState.instituteName else currentState.selectedInstitute
                            )) {
                                is AuthResult.Success -> {
                                    _effect.send(SignUpEffect.HideLoading)
                                    _effect.send(NavigateToNextScreen())
                                }

                                is AuthResult.Error -> {
                                    _effect.send(SignUpEffect.HideLoading)
                                    _effect.send(ShowError(result.errorMessage))
                                }

                                is AuthResult.Loading -> {
                                    // Handle loading state if needed
                                }
                            }
                        }
                    }

                    SignUpIntent.ToggleInstituteDropdown -> {
                        _uiState.value =
                            _uiState.value.copy(isInstituteDropdownExpanded = !_uiState.value.isInstituteDropdownExpanded)
                    }

                    is SignUpIntent.SelectInstitute -> {
                        _uiState.value = _uiState.value.copy(selectedInstitute = intent.institute)
                    }

                    is SignUpIntent.ToggleCheckbox -> {
                        _uiState.value = _uiState.value.copy(isCheckboxChecked = intent.isChecked)
                    }

                    is SignUpIntent.InstituteNameChanged -> {
                        _uiState.value = _uiState.value.copy(instituteName = intent.name)
                    }

                    SignUpIntent.NavigateToLogin -> {
                        _effect.send(NavigateToLogin)

                    }
                }
            }
        }
    }

    fun sendIntent(intent: SignUpIntent) {
        viewModelScope.launch {
            _intent.send(intent)
        }
    }
}

data class SignUpState(
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isInstituteDropdownExpanded: Boolean = false,
    val selectedInstitute: String = "",
    val instituteName: String = "",
    val isCheckboxChecked: Boolean = false
)
