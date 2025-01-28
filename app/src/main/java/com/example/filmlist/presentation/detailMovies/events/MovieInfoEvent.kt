package com.example.filmlist.presentation.detailMovies.events

import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface MovieInfoEvent: BasedViewModel.Event{
    class getMovieInfo(val newId: String): MovieInfoEvent
    class addMovieToDataBase(val state: MovieState):MovieInfoEvent
    class isMovieInBdCheck(val id: Int): MovieInfoEvent
}

