package com.example.orb_ed.presentation.screens.auth.signup

sealed class SignUpIntent {
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PhoneNumberChanged(val phoneNumber: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent()
    data class TogglePasswordVisibility(val isVisible: Boolean) : SignUpIntent()
    data class ToggleConfirmPasswordVisibility(val isVisible: Boolean) : SignUpIntent()
    object SignUp : SignUpIntent()
    object ToggleInstituteDropdown : SignUpIntent()
    data class SelectInstitute(val institute: String) : SignUpIntent()
    data class ToggleCheckbox(val isChecked: Boolean) : SignUpIntent()
    data class InstituteNameChanged(val name: String) : SignUpIntent()

    object NavigateToLogin : SignUpIntent()
}
