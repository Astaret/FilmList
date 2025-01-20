package com.example.filmlist.presentation.states

import com.example.filmlist.data.local.enteties.MovieEntity

data class TopMovieState(
    val movieList : List<MovieEntity>,
    val currentPage: Int,
    val totalPages: Int
)