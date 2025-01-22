package com.example.filmlist.presentation.topMovies.states

import com.example.filmlist.domain.models.Movie

data class TopMovieState(
    val movieList : List<Movie>,
    val currentPage: Int,
    val totalPages: Int
)