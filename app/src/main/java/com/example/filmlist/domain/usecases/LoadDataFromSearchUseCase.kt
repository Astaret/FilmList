package com.example.filmlist.domain.usecases

import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class LoadDataFromSearchUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend fun loadDataFromSearch(query:String) = repository.loadDataFromSearch(query)
}