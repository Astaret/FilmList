package com.example.filmlist.presentation.searchMovies.states

import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class SearchState(
    override val isLoading: com.example.domain.states.LoadingState = com.example.domain.states.LoadingState.Loading,
    val movieList: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<Movie> = emptyList()
): BasedViewModel.State