package com.example.orb_ed.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventBus {
    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun postEvent(event: AuthEvent) {
        _authEvents.emit(event)
    }
}

sealed class AuthEvent {
    data class AuthenticationFailed(val message: String) : AuthEvent()
}
