package com.example.data.mappers

import com.example.data.BuildConfig
import com.example.domain.types.EntityType
import com.example.domain.entities.dto_entities.MovieDto
import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.entities.Movie
import com.example.domain.types.MovieType
import com.example.domain.types.MovieStatus

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
    entityType: EntityType = EntityType.EMPTY
) = MovieIdEntity(
    id = id,
    entityType = entityType)

fun List<MovieDto>.listMovieDtoToListMovie() = map { it.dtoToMovie() }
fun List<Movie>.listMovieToListMovieDto() = map { it.movieToDto() }

fun MovieType.toEntityType(): EntityType {
    return when (this) {
        MovieType.ISFAVORITE -> EntityType.ISFAVORITE
        MovieType.ISBOUGHT -> EntityType.ISBOUGHT
        MovieType.INSTORE -> EntityType.INSTORE
       MovieType.EMPTY -> EntityType.EMPTY
    }
}


fun MovieType.toMovieStatus(): MovieStatus {
    return when (this) {
        MovieType.ISFAVORITE -> MovieStatus.FAVORITE
        MovieType.ISBOUGHT -> MovieStatus.BOUGHT
        MovieType.INSTORE -> MovieStatus.INSTORE
        MovieType.EMPTY -> MovieStatus.EMPTY
    }
}
