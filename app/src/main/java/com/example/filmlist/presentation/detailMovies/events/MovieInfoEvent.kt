package com.example.filmlist.presentation.detailMovies.events

import com.example.domain.entities.Movie
import com.example.domain.types.MovieType
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface MovieInfoEvent : BasedViewModel.Event {
    class AddMovieToDataBase(val state: MovieType, val movie: Movie) : MovieInfoEvent
    class DeleteMovieFromDataBase(val movie: Movie) : MovieInfoEvent
    class GetAllInfoAboutMovie(val id: String) : MovieInfoEvent
}

