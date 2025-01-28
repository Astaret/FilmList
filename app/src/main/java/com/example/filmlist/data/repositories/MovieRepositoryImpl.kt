package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.local.db.EntityState
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.data.mappers.dtoToMovie
import com.example.filmlist.data.mappers.listMovieDtoToListMovie
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.data.mappers.toEntityState
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : MovieRepository {


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


    override suspend fun getMovieListFromBd(state: ListMovieState): List<Movie> {
        return coroutineScope {
                movieDao.getMovieListFromBd(state).map { storeMovie ->
                    async {
                        apiService.getMovieInfo(storeMovie.id).dtoToMovie()
                    }
                }.awaitAll()
            }
        }

    override suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState) {
        Log.d("Movie", "putMovieToDb: ${movie.title} to ${stateOfMovie.name}")
        val existingMovie = movieDao.getMovieById(movie.id)
        if (existingMovie != null) {
            movieDao.updateMovieField(movie.id, stateOfMovie.toEntityState())
        } else {
            val movieEntity = movie.movieToMovieEntity(entityState = stateOfMovie.toEntityState())
            movieDao.insertInMovieList(
                movieEntity.copy(id = movie.id)
            )
        }

    }
}