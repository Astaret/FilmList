package com.example.filmlist.domain.usecases.LoadUseCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadFavMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun putFavMovieToDb(movie: Movie) = movieRepository.saveFavMovieToDb(movie)
}