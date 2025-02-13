package com.example.filmlist.presentation

import com.example.domain.entities.Movie
import com.example.domain.types.MovieType
import com.example.domain.types.MovieStatus

fun MovieType.toMovieStatus(): MovieStatus {
    return when (this) {
        MovieType.ISFAVORITE -> MovieStatus.FAVORITE
        MovieType.ISBOUGHT -> MovieStatus.BOUGHT
        MovieType.INSTORE -> MovieStatus.INSTORE
        MovieType.EMPTY -> MovieStatus.EMPTY
    }
}

fun List<Movie>.toBoughtMovies(): List<Movie> =
    this.map { movie -> movie.copy(price = movie.rating.toFloat() * 550.20f) }