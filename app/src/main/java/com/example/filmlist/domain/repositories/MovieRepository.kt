package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun loadData()

    suspend fun loadDataFromSearch(query: String): Flow<List<MovieEntity>>

    fun getMovieInfoList(): Flow<List<MovieEntity>>

}