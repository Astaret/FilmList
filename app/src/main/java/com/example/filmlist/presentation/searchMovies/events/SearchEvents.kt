package com.example.filmlist.presentation.searchMovies.events

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface SearchEvents: BasedViewModel.Event {
    class SearchChange(val newSearch: String): SearchEvents
}