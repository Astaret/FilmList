package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.data.mappers.dtoToMovie
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.states.ListMovieState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : MovieRepository {


    override suspend fun loadData(loadPage: Int): List<Movie> {
        return with(apiService.getTopRatedMovies(page = loadPage)) {
            val movieList = MovieList.map {
                it.dtoToMovie()
            }
            movieList
        }
    }

    override suspend fun loadDataFromSearch(query: String): Flow<List<Movie>> {
        return flow {
            val movieList = apiService.searchMovies(query).MovieList.map {
                it.dtoToMovie()
            }
            emit(movieList)
        }
    }

    override suspend fun getTotalPages(): Int {
        return apiService.getTopRatedMovies(page = 1).totalPages.toInt()
    }

    override suspend fun getMovieInfo(id: Int): Movie {
        return apiService.getMovieInfo(movieId = id).dtoToMovie()
    }


    override suspend fun getMovieByIdFromBd(id: Int): MovieIdEntity {
        return coroutineScope {
            movieDao.getMovieById(id)
        }
    }

    override suspend fun getMovieListFromBd(state: ListMovieState): List<Movie> {
        return coroutineScope {
            val movieList = movieDao.getMovieListFromBd(state).map { storeMovie ->
                async {
                    apiService.getMovieInfo(storeMovie.id).dtoToMovie()
                }
            }.awaitAll()
            movieList
        }
    }

    override suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState) {
        Log.d("Movie", "putMovieToDb: ${movie.title} to ${stateOfMovie.name}")
        if (movieDao.getMovieById(movie.id) != null) {
            movieDao.updateMovieField(movie.id, stateOfMovie.name)
        } else {
            movieDao.insertInMovieList(movie.movieToMovieEntity().apply { stateOfMovie.name })
        }
    }

}