package com.example.orb_ed.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel for MVI architecture.
 * @param State The type of the UI state
 * @param Event The type of the UI events
 * @param Effect The type of the side effects
 */
abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    // Create Initial State of the view
    private val initialState: State by lazy { createInitialState() }

    // Get the current state
    protected var currentState: State = initialState
        private set

    // StateFlow for the UI state
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    // StateFlow for side effects
    private val _effect: MutableStateFlow<Effect?> = MutableStateFlow(null)
    val effect: StateFlow<Effect?> = _effect.asStateFlow()

    // Must be implemented in child classes to define the initial state
    protected abstract fun createInitialState(): State

    // Must be implemented in child classes to handle events
    protected abstract fun handleEvent(event: Event)

    /**
     * Set new state
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _state.value = newState
        currentState = newState
    }

    /**
     * Set new effect
     */
    protected fun setEffect(builder: () -> Effect) {
        _effect.value = builder()
    }

    /**
     * Clear the current effect
     */
    fun clearEffect() {
        _effect.value = null
    }

    /**
     * Process the event in a coroutine scope
     */
    fun onEvent(event: Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }
}

/**
 * Base interface for UI state
 */
interface UiState

/**
 * Base interface for UI events
 */
interface UiEvent

/**
 * Base interface for UI side effects
 */
interface UiEffect
