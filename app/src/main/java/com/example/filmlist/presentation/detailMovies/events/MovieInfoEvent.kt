package com.example.filmlist.presentation.detailMovies.events

import com.example.filmlist.domain.states.MovieState

sealed interface MovieInfoEvent{
    class getMovieInfo(val newId: String): MovieInfoEvent
    class addMovieToDataBase(val state: MovieState):MovieInfoEvent
    class isMovieInBdCheck(val id: Int): MovieInfoEvent
}

