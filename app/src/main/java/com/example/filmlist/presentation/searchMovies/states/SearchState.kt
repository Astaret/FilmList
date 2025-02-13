package com.example.filmlist.presentation.searchMovies.states

import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState

data class SearchState(
    val movieList: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<Movie> = emptyList()
) : BasedViewModel.State.ScreenState