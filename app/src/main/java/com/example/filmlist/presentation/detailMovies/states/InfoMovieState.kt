package com.example.filmlist.presentation.detailMovies.states

import android.graphics.Bitmap
import com.example.domain.enteties.Movie
import com.example.domain.states.StatusMovie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class InfoMovieState(
    val id: String = "",
    val movieEntity: Movie = Movie(
        0,
        "",
        "",
        "",
        "",
        "",
        0f
    ),
    val statusMovie: StatusMovie = StatusMovie.EMPTY,
    val qrCode: Bitmap? = null
): BasedViewModel.State.ScreenState

