package com.example.filmlist.presentation.topMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState

data class TopMovieState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val movieList : List<Movie> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 2
): BasedViewModel.State