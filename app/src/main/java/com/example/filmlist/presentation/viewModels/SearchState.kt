package com.example.filmlist.presentation.viewModels

import com.example.filmlist.data.local.enteties.MovieEntity

data class SearchState(
    val movieList: List<MovieEntity> = emptyList(),
    val searchQuery: String = "",
    val searchResult: List<MovieEntity> = emptyList()
)