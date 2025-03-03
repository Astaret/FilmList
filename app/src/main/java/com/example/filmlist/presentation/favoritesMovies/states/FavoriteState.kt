package com.example.filmlist.presentation.favoritesMovies.states

import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class FavoriteState(
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isEmpty()
) : BasedViewModel.State.ScreenState
