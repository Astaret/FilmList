package com.example.filmlist.presentation.detailMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class InfoMovieState(
    val id: String = "",
    val movieEntity: Movie = Movie(0,"","","","","",0f),
    val statusMovie: StatusMovie = StatusMovie.EMPTY
): BasedViewModel.State

enum class StatusMovie(){
    FAVORITE, INSTORE, EMPTY, BOUGHT
}