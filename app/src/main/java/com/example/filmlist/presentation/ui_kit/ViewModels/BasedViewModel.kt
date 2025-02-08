package com.example.filmlist.presentation.ui_kit.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BasedViewModel<State : BasedViewModel.State, Event : BasedViewModel.Event>(
    initialState: State
) : ViewModel() {

    interface State {
        val isLoading: LoadingState
    }

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

    fun receiveEvent(event: Event) {
        viewModelScope.launch(dispatcher) {
            _state.emit(handleEvent(event))
        }
    }

    protected fun <T> handleOperation(
        operation: suspend () -> Flow<T>,
        onSuccess: (T) -> State,
        onError: (Throwable) -> State
    ) {
        viewModelScope.launch(dispatcher) {
            operation()
                .catch { throwable ->
                    setState { onError(throwable) }
                }.collect { value ->
                    setState { onSuccess(value) }
                }
        }
    }
}