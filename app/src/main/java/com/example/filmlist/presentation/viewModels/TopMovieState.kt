package com.example.filmlist.presentation.viewModels

import com.example.filmlist.data.local.enteties.MovieEntity
import kotlinx.coroutines.flow.Flow

data class TopMovieState(
    val movieList : List<MovieEntity>,
    val currentPage: Int,
    val totalPages: Int
)