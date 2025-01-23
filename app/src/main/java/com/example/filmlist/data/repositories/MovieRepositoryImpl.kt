package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.mappers.dtoToMovie
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
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

    override suspend fun loadFavMovieToDb(mov: Movie) {
        if (movieDao.getMovieById(mov.id) == null ) {
            Log.d("Movie", "loadFavMovieToDb: insert")
            movieDao.insertInMovieList(mov.movieToMovieEntity().apply { isFavorite = 1 })
        }else {
            Log.d("Movie", "loadFavMovieToDb: update")
            movieDao.updateFavField(mov.id, 1)
        }
    }

    override suspend fun loadStoreMovieToDb(movie: Movie) {
        if (movieDao.getMovieById(movie.id) == null ) {
            Log.d("Movie", "loadFavMovieToDb: insert")
            movieDao.insertInMovieList(movie.movieToMovieEntity().apply { isInStore = 1 })
            Log.d("Movie", "loadStoreMovieToDb: SUCCES!! INSERT ")
        }else {
            movieDao.updateStoreField(movie.id, 1)
            Log.d("Movie", "loadStoreMovieToDb: SUCCES!! UPDATE ")
        }
    }

    override suspend fun getFavoriteMovie(): List<Movie>{
        Log.d("Movie", "getFavoriteMovie: ${movieDao.getFavoriteMovieList()}")
        return with(movieDao.getFavoriteMovieList()){
            val movieList = this.map {
                apiService.getMovieInfo(it.id).dtoToMovie()
            }
            movieList
        }
    }

    override suspend fun getStoreMovie(): List<Movie> {
        Log.d("Movie", "getStoreMovie: ${movieDao.getFromStoreMovieList()}")
        return with(movieDao.getFromStoreMovieList()){
            val movieList = this.map {
                apiService.getMovieInfo(it.id).dtoToMovie()
            }
            movieList
        }
    }

}