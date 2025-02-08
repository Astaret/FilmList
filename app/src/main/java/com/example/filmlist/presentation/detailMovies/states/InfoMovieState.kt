package com.example.filmlist.presentation.detailMovies.states

import android.graphics.Bitmap
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState

data class InfoMovieState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val id: String = "",
    val movieEntity: Movie = Movie(0,"","","","","",0f),
    val statusMovie: StatusMovie = StatusMovie.EMPTY,
    val qrCode: Bitmap? = null
): BasedViewModel.State

enum class StatusMovie(){
    FAVORITE, INSTORE, EMPTY, BOUGHT
}