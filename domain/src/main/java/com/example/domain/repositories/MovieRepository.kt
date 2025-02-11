package com.example.domain.repositories

import com.example.data.local.db_enteties.MovieIdEntity
import com.example.domain.enteties.Movie
import com.example.domain.states.ListMovieState
import com.example.domain.states.MovieState
interface MovieRepository {

    suspend fun loadData(page: Int): List<Movie>

    suspend fun loadDataFromSearch(query: String): List<Movie>

    suspend fun getTotalPages(): Int

    suspend fun getMovieInfo(id: Int): Movie

    suspend fun getMovieByIdFromBd(id:Int): com.example.data.local.db_enteties.MovieIdEntity?

    suspend fun getMovieListFromBd(state: ListMovieState): List<Movie>

    suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState)

}