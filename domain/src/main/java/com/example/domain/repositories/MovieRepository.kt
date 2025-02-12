package com.example.domain.repositories

import com.example.domain.entities.Movie
import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.states.ListMovieState
import com.example.domain.states.MovieState
interface MovieRepository {

    suspend fun loadData(page: Int): List<Movie>

    suspend fun loadDataFromSearch(query: String): List<Movie>

    suspend fun getTotalPages(): Int

    suspend fun getMovieInfo(id: Int): Movie

    suspend fun getMovieByIdFromBd(id:Int): MovieIdEntity?

    suspend fun getMovieListFromBd(state: ListMovieState): List<Movie>

    suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState)

}