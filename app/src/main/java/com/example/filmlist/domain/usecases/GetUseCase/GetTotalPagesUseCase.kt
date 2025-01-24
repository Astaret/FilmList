package com.example.filmlist.domain.usecases.GetUseCase

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetTotalPagesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun getTotalPages() = repository.getTotalPages()
}