package com.example.filmlist.presentation.storeMovies.states

import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class StoreMovState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isEmpty(),
    val totalPrice: Double = 0.0,
    val ratingOfMovie: String = ""
) : BasedViewModel.State