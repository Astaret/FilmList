package com.example.filmlist.data.mappers

import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.data.web.api.ApiFactory.IMG_URL
import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.MovieState

fun MovieDto.dtoToMovie() = Movie(
    id = id,
    origLang = origLang,
    overview = overview,
    poster = IMG_URL + poster,
    title = title,
    rating = rating
)

fun Movie.movieToMovieEntity(
    isFavorite: Int = 0,
    isInStore: Int = 0,
    isBought: Int = 0
) = MovieIdEntity(
    id = id,
    isFavorite = isFavorite,
    isInStore = isInStore,
    isBought = isBought)


