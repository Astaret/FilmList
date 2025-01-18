package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.dsl.errorHandled
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.data.mappers.dtoToMovieEntity
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : MovieRepository {


    override suspend fun loadData(loadPage: Int): List<MovieEntity> {
        return with(apiService.getTopRatedMovies(page = loadPage)) {
            val movieList = MovieList.map { it.dtoToMovieEntity() }
            movieList.forEach{
                Log.d("Movie", "loadData SUCCES! -> $it")
            }
            movieList
        }
    }

    override suspend fun loadDataFromSearch(query: String): Flow<List<MovieEntity>> {
        return flow {
            val movieList = apiService.searchMovies(query).MovieList.map {
                it.dtoToMovieEntity()
            }
            emit(movieList)
        }
    }

    override suspend fun getTotalPages(): Int {
        return apiService.getTopRatedMovies(page = 1).totalPages.toInt()
    }


    override fun getMovieInfoList(): Flow<List<MovieEntity>> {
        return movieDao.getMovieList()
    }
}