package com.example.data.mappers

import com.example.domain.states.EntityState
import com.example.data.web.dtos.MovieDto
import com.example.data.local.db_enteties.MovieIdEntity
import com.example.domain.enteties.Movie
import com.example.domain.states.MovieState
import com.squareup.picasso.BuildConfig

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
        ISFAVORITE -> StatusMovie.FAVORITE
        ISBOUGHT -> StatusMovie.BOUGHT
        INSTORE -> StatusMovie.INSTORE
        EMPTY -> StatusMovie.EMPTY
    }
}
