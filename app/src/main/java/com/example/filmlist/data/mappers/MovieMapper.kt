package com.example.filmlist.data.mappers

import com.example.filmlist.data.local.db.EntityState
import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.data.web.api.ApiFactory.IMG_URL
import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.presentation.detailMovies.states.StatusMovie

fun MovieDto.dtoToMovie() = Movie(
    id = id,
    origLang = origLang,
    overview = overview,
    poster = IMG_URL + poster,
    title = title,
    rating = rating
)

fun Movie.movieToMovieEntity(
    entityState: EntityState = EntityState.EMPTY
) = MovieIdEntity(
    id = id,
    entityState = entityState)

fun List<MovieDto>.listMovieDtoToListMovie() = map { it.dtoToMovie() }

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
