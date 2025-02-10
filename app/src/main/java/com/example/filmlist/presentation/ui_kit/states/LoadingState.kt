package com.example.filmlist.presentation.ui_kit.states

sealed class LoadingState {
    object Loading: LoadingState()
    object Succes: LoadingState()
    object Error: LoadingState()
}