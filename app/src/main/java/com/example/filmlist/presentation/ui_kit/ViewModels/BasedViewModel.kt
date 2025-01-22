package com.example.filmlist.presentation.ui_kit.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BasedViewModel<State: Any, Event>: ViewModel() {
    private val _state = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> = _state


    protected fun updateState(ust: (State) -> State){
        _state.update { currentState ->
            currentState?.let(ust)
        }
    }
    abstract fun send(event: Event)
}