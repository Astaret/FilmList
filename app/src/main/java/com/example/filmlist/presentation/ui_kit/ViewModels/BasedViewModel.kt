package com.example.filmlist.presentation.ui_kit.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BasedViewModel<State : BasedViewModel.State, Event : BasedViewModel.Event>(
    initialState: State
) : ViewModel() {

    interface State

    interface Event

    private val dispatcher = Dispatchers.IO

    private val _state = MutableStateFlow<State>(initialState)
    val state: StateFlow<State> = _state

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> get() = _event

    protected fun setState(reducer: State.() -> State) {
        _state.update(reducer)

    }

    internal abstract fun handleEvent(event: Event): State

    fun receiveEvent(
        event: Event) {
        viewModelScope.launch(dispatcher) {
            _state.emit(handleEvent(event))
        }
    }


    protected fun <T> handleOperation(
        operation: suspend () -> Flow<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            operation().collect {
                onSuccess(it)
            }
        }
    }
}