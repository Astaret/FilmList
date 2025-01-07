package com.example.filmlist.domain.usecases

import androidx.lifecycle.LiveData
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieInfoListUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getMovieInfoList(): LiveData<List<MovieEntity>> { return repository.getMovieInfoList() }
}