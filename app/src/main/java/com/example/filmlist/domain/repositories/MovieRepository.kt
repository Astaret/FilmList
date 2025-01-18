package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadData(page: Int): List<MovieEntity>

    suspend fun loadDataFromSearch(query: String): Flow<List<MovieEntity>>

    suspend fun getTotalPages(): Int

    fun getMovieInfoList(): Flow<List<MovieEntity>>

}