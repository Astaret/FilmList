package com.example.domain.states

sealed class LoadingState {
    object Loading: LoadingState()
    object Succes: LoadingState()
    object Error: LoadingState()
}