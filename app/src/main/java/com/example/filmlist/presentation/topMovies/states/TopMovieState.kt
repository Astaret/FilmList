package com.example.filmlist.presentation.topMovies.states

import com.example.filmlist.domain.models.Movie

data class TopMovieState(
    val movieList : List<Movie> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 2
)