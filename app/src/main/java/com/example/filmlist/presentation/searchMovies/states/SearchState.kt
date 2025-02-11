package com.example.filmlist.presentation.searchMovies.states

import com.example.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState

data class SearchState(
    override val isLoading: com.example.domain.states.LoadingState = com.example.domain.states.LoadingState.Loading,
    val movieList: List<com.example.domain.models.Movie> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<com.example.domain.models.Movie> = emptyList()
): BasedViewModel.State