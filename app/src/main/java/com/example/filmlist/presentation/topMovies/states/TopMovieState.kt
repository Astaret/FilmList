package com.example.filmlist.presentation.topMovies.states

import com.example.domain.enteties.Movie
import com.example.domain.states.LoadingState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class TopMovieState(
    override val isLoading:LoadingState = LoadingState.Loading,
    val movieList : List<Movie> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 2
): BasedViewModel.State