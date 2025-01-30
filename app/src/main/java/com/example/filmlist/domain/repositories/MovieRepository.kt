package com.example.filmlist.domain.repositories

import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadData(page: Int): List<Movie>

    suspend fun loadDataFromSearch(query: String): List<Movie>

    suspend fun getTotalPages(): Int

    suspend fun getMovieInfo(id: Int): Movie

    suspend fun getMovieByIdFromBd(id:Int): MovieIdEntity?

    suspend fun getMovieListFromBd(state: ListMovieState): List<Movie>

    suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState)

}