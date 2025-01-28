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
        return apiService.getTopRatedMovies(page = loadPage).MovieList.map {
            it.dtoToMovie()
        }
    }

    override suspend fun loadDataFromSearch(query: String): List<Movie> {
        return apiService.searchMovies(query).MovieList.map {
            it.dtoToMovie()
        }
    }

    override suspend fun getTotalPages(): Int {
        return apiService.getTopRatedMovies(page = 1).totalPages.toInt()
    }

    override suspend fun getMovieInfo(id: Int): Movie{
        return apiService.getMovieInfo(movieId = id).dtoToMovie()
    }


    override suspend fun getMovieByIdFromBd(id: Int): MovieIdEntity? {
        return coroutineScope {
            movieDao.getMovieById(id)
        }
    }

    override suspend fun getMovieListFromBd(state: ListMovieState): List<Movie> {
        return coroutineScope {
                movieDao.getMovieListFromBd(state).map { storeMovie ->
                    async {
                        apiService.getMovieInfo(storeMovie.id).dtoToMovie()
                    }
                }.awaitAll()
            }
        }

    override suspend fun putMovieToDb(movie: Movie, stateOfMovie: MovieState): String {
        Log.d("Movie", "putMovieToDb: ${movie.title} to ${stateOfMovie.name}")
        val existingMovie = movieDao.getMovieById(movie.id)
        if (existingMovie != null) {
            movieDao.updateMovieField(movie.id, stateOfMovie.name)
            return ("updated")
        } else {
            val movieEntity = when (stateOfMovie) {
                MovieState.ISFAVORITE -> movie.movieToMovieEntity(isFavorite = 1)
                MovieState.ISBOUGHT -> movie.movieToMovieEntity(isBought = 1)
                MovieState.INSTORE -> movie.movieToMovieEntity(isInStore = 1)
                MovieState.EMPTY -> movie.movieToMovieEntity()
            }
            movieDao.insertInMovieList(
                movieEntity.copy(id = movie.id)
            )
            return ("added")
        }

    }
}