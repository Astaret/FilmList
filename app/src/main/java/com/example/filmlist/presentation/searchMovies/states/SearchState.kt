package com.example.filmlist.presentation.searchMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class SearchState(
    val movieList: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<Movie> = emptyList()
): BasedViewModel.State