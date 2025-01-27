package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadStoreMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun putStoreMovieToDb(movie: Movie) = movieRepository.putStoreMovieToDb(movie)
}