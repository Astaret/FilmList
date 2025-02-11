package com.example.filmlist.presentation.detailMovies.states

import android.graphics.Bitmap
import com.example.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState

data class InfoMovieState(
    override val isLoading: com.example.domain.states.LoadingState = com.example.domain.states.LoadingState.Loading,
    val id: String = "",
    val movieEntity: com.example.domain.models.Movie = com.example.domain.models.Movie(
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
): BasedViewModel.State

enum class StatusMovie(){
    FAVORITE, INSTORE, EMPTY, BOUGHT
}