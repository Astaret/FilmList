package com.example.filmlist.presentation.favoritesMovies.states

import com.example.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState

data class FavoriteState(
    override val isLoading: com.example.domain.states.LoadingState = com.example.domain.states.LoadingState.Loading,
    val movieList: List<com.example.domain.models.Movie> = emptyList(),
    val empty: Boolean = movieList.isEmpty()
): BasedViewModel.State
