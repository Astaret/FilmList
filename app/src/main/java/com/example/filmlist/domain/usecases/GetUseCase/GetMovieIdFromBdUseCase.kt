package com.example.filmlist.domain.usecases.GetUseCase

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieIdFromBdUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend fun getMovieByIdFromBd(id: Int) = repository.getMovieByIdFromBd(id)
}