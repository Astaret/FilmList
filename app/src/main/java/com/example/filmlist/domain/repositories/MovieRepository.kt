package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadData(page:Int) : List<Movie>

    fun getMovieInfoList(): Flow<List<MovieEntity>>

}