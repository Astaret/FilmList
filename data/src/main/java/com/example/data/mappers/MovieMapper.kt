package com.example.data.mappers

import com.example.data.BuildConfig
import com.example.domain.states.EntityState
import com.example.domain.entities.dto_entities.MovieDto
import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.entities.Movie
import com.example.domain.states.MovieState
import com.example.domain.states.StatusMovie

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
