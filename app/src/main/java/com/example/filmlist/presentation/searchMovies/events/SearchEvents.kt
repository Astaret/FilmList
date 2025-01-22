package com.example.filmlist.presentation.searchMovies.events

sealed interface SearchEvents{
    class SearchChange(val newSearch: String): SearchEvents
}