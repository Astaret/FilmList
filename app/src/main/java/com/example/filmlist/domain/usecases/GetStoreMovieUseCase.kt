package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetStoreMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getStoreMovie() = movieRepository.getStoreMovie()
}