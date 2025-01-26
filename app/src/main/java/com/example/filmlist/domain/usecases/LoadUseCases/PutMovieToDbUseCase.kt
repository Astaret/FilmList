package com.example.filmlist.domain.usecases.LoadUseCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class PutMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun putMovieToDb(
        movie: Movie, stateOfMovie: MovieState
    ) = movieRepository.putMovieToDb(movie, stateOfMovie)
}