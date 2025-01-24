package com.example.filmlist.presentation.detailMovies.states

import com.example.filmlist.domain.models.Movie

data class InfoMovieState(
    val id: String,
    val movieEntity: Movie,
    val isFavorite: Boolean,
    val isBought: Boolean
)

enum class StatusMovie(){

}