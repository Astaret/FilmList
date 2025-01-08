package com.example.filmlist.data.mappers

import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.data.web.api.ApiFactory.IMG_URL
import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.domain.models.Movie

fun MovieDto.dtoToMovie(): Movie {
    return Movie(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title,
        rating = rating
    )
}

fun MovieEntity.entityToMovie(): Movie {
    return Movie(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title,
        rating = rating
    )
}

fun Movie.movieToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title,
        rating = rating
    )
}

fun MovieDto.dtoToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = IMG_URL + poster,
        title = title,
        rating = rating
    )
}