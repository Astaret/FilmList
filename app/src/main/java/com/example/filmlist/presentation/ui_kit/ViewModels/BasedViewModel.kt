package com.example.filmlist.presentation.ui_kit.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BasedViewModel<State: Any, Event: Any>(
    initialState: State
): ViewModel() {

    private val _state = MutableStateFlow<State>(initialState)
    val state: StateFlow<State> = _state

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> get() = _event

    protected fun setState(reducer: State.() -> State){
        _state.update(reducer)

    }

    abstract fun send(event: Event)

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun launchInScope(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                Log.d("Movie", "launchInScope: $e")
            }
        }
    }
}