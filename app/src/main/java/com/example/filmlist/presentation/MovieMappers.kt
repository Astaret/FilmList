package com.example.filmlist.presentation

import com.example.domain.states.MovieState
import com.example.domain.states.StatusMovie
fun MovieState.toMovieStatus(): StatusMovie {
    return when (this) {
        MovieState.ISFAVORITE -> StatusMovie.FAVORITE
        MovieState.ISBOUGHT -> StatusMovie.BOUGHT
        MovieState.INSTORE -> StatusMovie.INSTORE
        MovieState.EMPTY -> StatusMovie.EMPTY
    }
}
