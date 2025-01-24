package com.example.filmlist.domain.usecases.LoadUseCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadBoughtMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun saveBoughtMovies(movie: Movie) = movieRepository.saveBoughtMovieToDb(movie)
}