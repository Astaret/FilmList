package com.example.filmlist.presentation.topMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class TopMovieState(
    val movieList : List<Movie> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 2
): BasedViewModel.State