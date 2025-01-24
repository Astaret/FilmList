package com.example.filmlist.presentation.libraryMovies.states

import com.example.filmlist.domain.models.Movie

data class LibraryState(
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isNullOrEmpty()
)
