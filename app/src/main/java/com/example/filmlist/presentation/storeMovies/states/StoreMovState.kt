package com.example.filmlist.presentation.storeMovies.states

import com.example.filmlist.domain.models.Movie

data class StoreMovState (
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = true
)