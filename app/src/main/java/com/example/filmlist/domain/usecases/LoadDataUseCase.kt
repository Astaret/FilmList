package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun loadData(page: Int) = repository.loadData(page)
}