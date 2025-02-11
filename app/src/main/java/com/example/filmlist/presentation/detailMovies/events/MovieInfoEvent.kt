package com.example.filmlist.presentation.detailMovies.events

import com.example.domain.states.MovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface MovieInfoEvent: BasedViewModel.Event{
    class GetMovieInfo(val newId: String): MovieInfoEvent
    class AddMovieToDataBase(val state: MovieState):MovieInfoEvent
    class IsMovieInBdCheck(val id: Int): MovieInfoEvent
    data object DeleteMovieFromDataBase : MovieInfoEvent
    class GetQrCode(val id: String): MovieInfoEvent
    class GetAllInfoAboutMovie(val id: String): MovieInfoEvent
}

