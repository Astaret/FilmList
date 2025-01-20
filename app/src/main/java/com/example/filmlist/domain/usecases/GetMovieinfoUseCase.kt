package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieinfoUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend fun getMovieInfo(id: Int) = repository.getMovieInfo(id)
}