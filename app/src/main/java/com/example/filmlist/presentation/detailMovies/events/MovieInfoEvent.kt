package com.example.filmlist.presentation.detailMovies.events

sealed interface MovieInfoEvent{
    class getMovieInfo(val newId: String): MovieInfoEvent
    class addMovieToFavorite():MovieInfoEvent
    class addMovieToStore():MovieInfoEvent
}

