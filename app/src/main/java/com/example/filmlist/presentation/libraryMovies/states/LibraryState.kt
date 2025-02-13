package com.example.filmlist.presentation.libraryMovies.states

import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class LibraryState(
    override val isLoading: LoadingState = LoadingState.Loading,
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isEmpty()
): BasedViewModel.State
