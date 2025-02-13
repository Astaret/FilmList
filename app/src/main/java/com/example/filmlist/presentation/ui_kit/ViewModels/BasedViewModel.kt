package com.example.filmlist.presentation.ui_kit.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    sealed interface State {
        object Loading: State
        class Error(val message: String) : State
        interface ScreenState : State
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
    protected fun handleError(throwable: Throwable): BasedViewModel.State {
        return BasedViewModel.State.Error(throwable.message.orEmpty())
    }

    protected fun <T> handleOperation(
        operation: suspend () -> Flow<T>,
        onSuccess: (T) -> State,
        onError: (Throwable) -> BasedViewModel.State
    ) {
        viewModelScope.launch(dispatcher) {
            operation()
                .catch { throwable ->
                    Log.d("Movie", "handleOperation: ${throwable.message}")
                    onError(throwable)
                }.collect { value ->
                    setState { onSuccess(value) }
                }
        }
    }
}