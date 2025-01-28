package com.example.filmlist.presentation.libraryMovies.states

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class LibraryState(
    val movieList: List<Movie> = emptyList(),
    val empty: Boolean = movieList.isNullOrEmpty()
): BasedViewModel.State
