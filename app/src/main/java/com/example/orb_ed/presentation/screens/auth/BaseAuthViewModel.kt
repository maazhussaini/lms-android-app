package com.example.orb_ed.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orb_ed.domain.model.ApiResult
import com.example.orb_ed.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel for authentication screens that handles common state and effects.
 */
abstract class BaseAuthViewModel<State : AuthState, Event : AuthEvent, Effect : AuthEffect> : ViewModel() {
    
    private val _state = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> = _state.asStateFlow()
    
    private val _effect = MutableSharedFlow<Effect?>(replay = 1)
    val effect: SharedFlow<Effect?> = _effect.asSharedFlow()
    
    /**
     * Updates the current state.
     */
    protected fun setState(newState: State) {
        _state.value = newState
    }
    
    /**
     * Emits a new effect.
     */
    protected fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
    
    /**
     * Clears the current effect.
     */
    fun clearEffect() {
        viewModelScope.launch {
            _effect.emit(null)
        }
    }
    
    /**
     * Handles the given event.
     */
    abstract fun onEvent(event: Event)
    
    /**
     * Handles API results and updates the state accordingly.
     */
    protected suspend fun <T> handleApiResult(
        result: ApiResult<T>,
        onSuccess: suspend (T) -> Unit
    ) {
        when (result) {
            is ApiResult.Loading -> setState(createLoadingState() as State)
            is ApiResult.Success -> {
                result.getOrNull()?.let { data ->
                    onSuccess(data)
                } ?: run {
                    setState(createErrorState("An unknown error occurred") as State)
                    setEffect(createErrorEffect("An unknown error occurred") as Effect)
                }
            }
            is ApiResult.Error -> {
                val errorMessage = result.exceptionOrNull()?.message ?: "An unknown error occurred"
                setState(createErrorState(errorMessage) as State)
                setEffect(createErrorEffect(errorMessage) as Effect)
            }
        }
    }
    
    /**
     * Creates a loading state.
     */
    protected abstract fun createLoadingState(): AuthState
    
    /**
     * Creates an error state with the given message.
     */
    protected abstract fun createErrorState(message: String): AuthState
    
    /**
     * Creates an error effect with the given message.
     */
    protected abstract fun createErrorEffect(message: String): Effect
}
