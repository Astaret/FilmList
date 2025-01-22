package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getFavoriteMovie() = movieRepository.getFavoriteMovie()
}