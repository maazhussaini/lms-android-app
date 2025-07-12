package com.example.orb_ed.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.repository.AuthRepository
import com.example.orb_ed.domain.usecase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    fun logout() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                authRepository.logout()
                _uiState.value = HomeUiState.LogoutSuccess
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Logout failed")
            }
        }
    }
}

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    object LogoutSuccess : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
