package com.example.filmlist.domain.usecases.GetUseCase

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetBoughtMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getBoughtMovies() = movieRepository.getBoughtMovies()
}