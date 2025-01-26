package com.example.filmlist.presentation.detailMovies.states

import com.example.filmlist.domain.models.Movie

data class InfoMovieState(
    val id: String = "",
    val movieEntity: Movie = Movie(0,"","","","","",0f),
    val statusMovie: StatusMovie = StatusMovie.EMPTY
)

enum class StatusMovie(){
    FAVORITE, INSTORE, EMPTY, BOUGHT
}