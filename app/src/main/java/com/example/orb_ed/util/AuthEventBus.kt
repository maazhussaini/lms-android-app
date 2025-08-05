package com.example.orb_ed.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventBus {
    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    private var hasPostedAuthFailed = false

    suspend fun postEvent(event: AuthEvent) {
        if (event is AuthEvent.AuthenticationFailed) {
            if (hasPostedAuthFailed) return // skip duplicate
            hasPostedAuthFailed = true
        }
        _authEvents.emit(event)
    }

    fun reset() {
        hasPostedAuthFailed = false
    }
}


sealed class AuthEvent {
    data class AuthenticationFailed(val message: String) : AuthEvent()
}
