package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.mappers.dtoToMovie
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
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

    override suspend fun saveFavMovieToDb(mov: Movie) {
        Log.d("Movie", "loadFavMovieToDb: insert")
        movieDao.insertInMovieList(mov.movieToMovieEntity().apply { isFavorite = 1 })

    }

    override suspend fun saveStoreMovieToDb(movie: Movie) {
        Log.d("Movie", "loadFavMovieToDb: insert")
        movieDao.insertInMovieList(movie.movieToMovieEntity().apply { isInStore = 1 })
        Log.d("Movie", "loadStoreMovieToDb: SUCCES!! INSERT ")
    }

    override suspend fun saveBoughtMovieToDb(movie: Movie) {
        movieDao.insertInMovieList(movie.movieToMovieEntity().apply {
            isBought = 1
        })
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        Log.d("Movie", "getFavoriteMovie: ${movieDao.getFavoriteMovieList()}")
        return coroutineScope {
            val movieList = movieDao.getFavoriteMovieList().map { favoriteMovie ->
                async {
                    apiService.getMovieInfo(favoriteMovie.id).dtoToMovie()
                }
            }.awaitAll()
            movieList
        }
    }

    override suspend fun getBoughtMovies(): List<Movie> {
        Log.d("Movie", "getBoughtMovies: ${movieDao.getFromStoreMovieList()}")

        return coroutineScope {
            val movieList = movieDao.getFromBoughtMovieList().map { storeMovie ->
                async {
                    apiService.getMovieInfo(storeMovie.id).dtoToMovie()
                }
            }.awaitAll()
            movieList
        }
    }

    override suspend fun getStoreMovie(): List<Movie> {
        Log.d("Movie", "getStoreMovie: ${movieDao.getFromStoreMovieList()}")

        return coroutineScope {
            val movieList = movieDao.getFromStoreMovieList().map { storeMovie ->
                async {
                    apiService.getMovieInfo(storeMovie.id).dtoToMovie()
                }
            }.awaitAll()
            movieList
        }
    }

}