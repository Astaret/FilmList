package com.example.filmlist.presentation.libraryMovies.events

sealed interface LibraryEvent  {
    object ShowAllBoughtMovies : LibraryEvent
}