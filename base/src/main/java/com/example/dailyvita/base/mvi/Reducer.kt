package com.example.dailyvita.base.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, I : UiEvent>(initialState: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: I) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)
    }

    abstract fun reduce(oldState: S, event: I)
}