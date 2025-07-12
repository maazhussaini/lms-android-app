package com.example.orb_ed.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.StateFlow

/**
 * Base composable function that handles common state and effect collection.
 * @param viewModel The ViewModel that extends BaseViewModel
 * @param onEffect A callback for handling side effects
 * @param content The main content of the screen
 */
@Composable
fun <State : UiState, Event : UiEvent, Effect : UiEffect, VM : BaseViewModel<State, Event, Effect>>
        BaseScreen(
    viewModel: VM,
    onEffect: (Effect) -> Unit,
    content: @Composable (state: State, onEvent: (Event) -> Unit) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)

    // Handle side effects
    LaunchedEffect(key1 = effect) {
        effect?.let { 
            onEffect(it)
            viewModel.clearEffect()
        }
    }

    // Render the content
    content(state, viewModel::onEvent)
}

/**
 * A simpler version of BaseScreen that doesn't handle effects.
 */
@Composable
fun <State : UiState, Event : UiEvent, VM : BaseViewModel<State, Event, *>>
        StatelessScreen(
    viewModel: VM,
    content: @Composable (state: State, onEvent: (Event) -> Unit) -> Unit
) {
    val state by viewModel.state.collectAsState()
    content(state, viewModel::onEvent)
}
