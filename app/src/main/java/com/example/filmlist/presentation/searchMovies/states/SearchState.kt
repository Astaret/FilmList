package com.example.filmlist.presentation.searchMovies.states

import com.example.filmlist.domain.models.Movie

data class SearchState(
    val movieList: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<Movie> = emptyList()
)