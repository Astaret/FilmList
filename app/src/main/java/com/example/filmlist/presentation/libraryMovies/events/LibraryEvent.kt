package com.example.filmlist.presentation.libraryMovies.events

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface LibraryEvent : BasedViewModel.Event {
    object ShowAllBoughtMovies : LibraryEvent
}