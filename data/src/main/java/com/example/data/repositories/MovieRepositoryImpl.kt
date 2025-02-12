package com.example.data.repositories

import android.util.Log
import com.example.data.local.db.MovieInfoDao
import com.example.data.mappers.dtoToMovie
import com.example.data.mappers.listMovieDtoToListMovie
import com.example.data.mappers.movieToMovieEntity
import com.example.data.mappers.toEntityState
import com.example.data.web.api.ApiService
import com.example.domain.entities.Movie
import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.states.ListMovieState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : com.example.domain.repositories.MovieRepository {


    override suspend fun loadData(loadPage: Int): List<Movie> {
        return apiService.getTopRatedMovies(page = loadPage).MovieList.listMovieDtoToListMovie()
    }

    override suspend fun loadDataFromSearch(query: String): List<Movie> {
        return apiService.searchMovies(query).MovieList.map {
            it.dtoToMovie()
        }
    }

    override suspend fun getTotalPages(): Int {
        return apiService.getTopRatedMovies(page = 1).totalPages.toInt()
    }

    override suspend fun getMovieInfo(id: Int): Movie {
        return apiService.getMovieInfo(movieId = id).dtoToMovie()
    }


    override suspend fun getMovieByIdFromBd(id: Int): MovieIdEntity? = movieDao.getMovieById(id)


    override suspend fun getMovieListFromBd(state: ListMovieState): List<Movie> = coroutineScope {
        movieDao.getMovieListFromBd(state).map {
            async {
                apiService.getMovieInfo(it.id).dtoToMovie()
            }.await()
        }
    }

    override suspend fun putMovieToDb(
        movie: Movie,
        stateOfMovie: com.example.domain.states.MovieState
    ) {
        Log.d("Movie", "putMovieToDb: ${movie.title} to ${stateOfMovie.name}")
        val movieEntity = movie.movieToMovieEntity(entityState = stateOfMovie.toEntityState())
        movieDao.insertInMovieList(movieEntity.copy(id = movie.id))
    }
}