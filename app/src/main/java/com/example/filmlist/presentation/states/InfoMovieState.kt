package com.example.filmlist.presentation.states

import com.example.filmlist.data.local.enteties.MovieEntity

data class InfoMovieState(
    val id: String,
    val movieEntity: MovieEntity
)