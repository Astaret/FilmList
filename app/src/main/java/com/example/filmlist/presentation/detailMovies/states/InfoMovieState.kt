package com.example.filmlist.presentation.detailMovies.states

import android.graphics.Bitmap
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import com.example.domain.types.MovieStatus
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class InfoMovieState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val id: String = "",
    val movieEntity: Movie? = null,
    val movieStatus: MovieStatus = MovieStatus.EMPTY,
    val qrCode: Bitmap? = null
) : BasedViewModel.State

