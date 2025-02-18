package com.example.filmlist.presentation.detailMovies.events

import com.example.domain.types.MovieType
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface MovieInfoEvent : BasedViewModel.Event {
    class AddMovieToDataBase(val state: MovieType) : MovieInfoEvent
    data object DeleteMovieFromDataBase : MovieInfoEvent
    class GetAllInfoAboutMovie(val id: String) : MovieInfoEvent
}

