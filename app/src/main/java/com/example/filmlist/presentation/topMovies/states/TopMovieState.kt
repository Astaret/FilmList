package com.example.filmlist.presentation.topMovies.states

import com.example.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState

data class TopMovieState(
    override val isLoading: com.example.domain.states.LoadingState = com.example.domain.states.LoadingState.Loading,
    val movieList : List<com.example.domain.models.Movie> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 2
): BasedViewModel.State