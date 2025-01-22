package com.example.filmlist.presentation.favoritesMovies.states

import com.example.filmlist.domain.models.Movie

data class FavoriteState(
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = true
)
