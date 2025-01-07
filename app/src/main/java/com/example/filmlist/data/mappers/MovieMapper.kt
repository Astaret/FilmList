package com.example.filmlist.data.mappers

import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.domain.models.Movie

fun MovieDto.dtoToMovie(): Movie {
    return Movie(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title
    )
}

fun MovieEntity.entityToMovie(): Movie {
    return Movie(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title
    )
}

fun Movie.movieToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title
    )
}

fun MovieDto.dtoToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        origLang = origLang,
        overview = overview,
        poster = poster,
        title = title
    )
}