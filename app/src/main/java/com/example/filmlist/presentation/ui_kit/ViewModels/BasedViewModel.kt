package com.example.filmlist.presentation.ui_kit.ViewModels

import androidx.lifecycle.AtomicReference
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BasedViewModel<LocalState : BasedViewModel.State.ScreenState, Event : BasedViewModel.Event>
    : ViewModel() {

    interface State {
        object Loading : State
        class Error(val message: String) : State
        interface ScreenState : State
    }

    protected abstract val cachedScreenState: AtomicReference<LocalState>

    interface Event

    private val dispatcher = Dispatchers.IO

    private val _state = MutableSharedFlow<State>()
    val state: SharedFlow<State> = _state

    private val _localScreenState = MutableSharedFlow<LocalState>()
    protected val localScreenState: SharedFlow<LocalState> = _localScreenState

    fun flowState(state: State) = flow { emit(state) }

    internal abstract suspend fun handleEvent(event: Event): Flow<LocalState>

    fun receiveEvent(event: Event) {
        viewModelScope.launch {
            handleEvent(event)
                .flowOn(dispatcher)
                .onEach { state ->
                    cachedScreenState.set(state)
                    _state.emit(state)
                }.collect {}
        }
    }

    protected suspend fun <T> handleOperation(
        operation: suspend () -> Flow<T>,
        withLoading: Boolean = true,
        onSuccess: suspend (T) -> State,
        onError: (Throwable) -> State = { State.Error(it.message ?: "Unknown error, try again") }
    ): Flow<State> = operation()
        .onStart { if (withLoading) _state.emit(State.Loading) }
        .map { onSuccess(it) }
        .catch { _state.emit(onError(it)) }
}