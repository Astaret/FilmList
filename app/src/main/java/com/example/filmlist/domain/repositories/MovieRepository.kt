package com.example.filmlist.domain.repositories

import androidx.lifecycle.LiveData
import com.example.filmlist.data.local.enteties.MovieEntity

interface MovieRepository {

    suspend fun loadData()

    fun getMovieInfoList(): LiveData<List<MovieEntity>>

}