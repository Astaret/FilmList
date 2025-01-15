package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadData()

    fun getMovieInfoList(): Flow<List<MovieEntity>>

}