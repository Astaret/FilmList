package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadData(page: Int): List<Movie>

    suspend fun loadDataFromSearch(query: String): Flow<List<Movie>> //flow

    suspend fun getTotalPages(): Int

    suspend fun getMovieInfo(id: Int): Movie

    suspend fun saveFavMovieToDb(movie: Movie)

    suspend fun saveStoreMovieToDb(movie: Movie)

    suspend fun saveBoughtMovieToDb(movie: Movie)

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun getStoreMovie(): List<Movie>

    suspend fun getBoughtMovies(): List<Movie>

    suspend fun getMovieByIdFromBd(id:Int): MovieIdEntity

}