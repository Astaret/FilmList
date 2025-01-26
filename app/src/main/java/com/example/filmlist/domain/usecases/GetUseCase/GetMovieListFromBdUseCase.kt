package com.example.filmlist.domain.usecases.GetUseCase

import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.states.ListMovieState
import javax.inject.Inject

class GetMovieListFromBdUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getMovieListFromBd(state: ListMovieState) = repository.getMovieListFromBd(state)
}