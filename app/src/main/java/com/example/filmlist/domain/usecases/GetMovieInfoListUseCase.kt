package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieInfoListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    fun getMovieInfoList() = repository.getMovieInfoList()
}