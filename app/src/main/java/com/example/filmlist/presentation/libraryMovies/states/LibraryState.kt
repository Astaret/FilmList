package com.example.filmlist.presentation.libraryMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState

data class LibraryState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isNullOrEmpty()
): BasedViewModel.State
