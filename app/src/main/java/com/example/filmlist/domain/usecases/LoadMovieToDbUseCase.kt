package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun loadMovieToDb(movie: Movie) = movieRepository.loadMovieToDb(movie)
}