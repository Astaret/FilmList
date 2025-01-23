package com.example.filmlist.data.mappers

import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.data.web.api.ApiFactory.IMG_URL
import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.domain.models.Movie

fun MovieDto.dtoToMovie() = Movie(
    id = id,
    origLang = origLang,
    overview = overview,
    poster = IMG_URL + poster,
    title = title,
    rating = rating
)

fun Movie.movieToMovieEntity(isFavorite: Int? = null, isInStore: Int? = null): MovieIdEntity {
    return MovieIdEntity(
        id = id,
        isFavorite = isFavorite ?: 0,
        isInStore = isInStore ?: 0
    )
}
