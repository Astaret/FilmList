package com.example.filmlist.presentation.detailMovies.events

import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface MovieInfoEvent: BasedViewModel.Event{
    class GetMovieInfo(val newId: String): MovieInfoEvent
    class AddMovieToDataBase(val state: MovieState):MovieInfoEvent
    class IsMovieInBdCheck(val id: Int): MovieInfoEvent
    data object DeleteMovieFromDataBase : MovieInfoEvent
}

