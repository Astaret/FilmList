package com.example.filmlist.presentation

import com.example.domain.states.EntityState
import com.example.domain.enteties.dto_enteties.MovieDto
import com.example.domain.enteties.db_enteties.MovieIdEntity
import com.example.domain.enteties.Movie
import com.example.domain.states.MovieState
import com.example.domain.states.StatusMovie
import com.example.myapp.BuildConfig

fun MovieDto.dtoToMovie() = Movie(
    id = id,
    origLang = origLang,
    overview = overview,
    poster = BuildConfig.IMG_API_URL + poster,
    title = title,
    rating = rating
)

fun Movie.movieToDto() = MovieDto(
    id = id,
    origLang = origLang,
    overview = overview,
    poster = BuildConfig.IMG_API_URL + poster,
    title = title,
    rating = rating
)

fun Movie.movieToMovieEntity(
    entityState: EntityState = EntityState.EMPTY
) = MovieIdEntity(
    id = id,
    entityState = entityState)

fun List<MovieDto>.listMovieDtoToListMovie() = map { it.dtoToMovie() }
fun List<Movie>.listMovieToListMovieDto() = map { it.movieToDto() }

fun MovieState.toEntityState(): EntityState {
    return when (this) {
        MovieState.ISFAVORITE -> EntityState.ISFAVORITE
        MovieState.ISBOUGHT -> EntityState.ISBOUGHT
        MovieState.INSTORE -> EntityState.INSTORE
       MovieState.EMPTY -> EntityState.EMPTY
    }
}


fun MovieState.toMovieStatus(): StatusMovie {
    return when (this) {
        MovieState.ISFAVORITE -> StatusMovie.FAVORITE
        MovieState.ISBOUGHT -> StatusMovie.BOUGHT
        MovieState.INSTORE -> StatusMovie.INSTORE
        MovieState.EMPTY -> StatusMovie.EMPTY
    }
}
