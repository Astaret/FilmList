package com.example.filmlist.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.data.mappers.dtoToMovieEntity
import com.example.filmlist.data.web.api.ApiFactory
import com.example.filmlist.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao
) : MovieRepository {
    private val movieListEntity = mutableListOf<MovieEntity>()

    override suspend fun loadData(token: String) {
        Log.d("Movie", "onCreate: coroutine")
        try {
            Log.d("Movie", "onCreate: try")
            val response = ApiFactory.api.getTopRatedMovies(token)
            with(response) {
                MovieList.forEach {
                    Log.d("Movie", "onCreateView: $it")
                    movieListEntity.add(it.dtoToMovieEntity())
                }
                movieDao.insertMovieList(movieListEntity)
                Log.d("Movie", "loadData: SUCCES!!!")
            }
        } catch (e: Exception) {
            Log.e("Movie", "onCreateView: Exception occurred", e)
        }
    }

    override fun getMovieInfoList(): LiveData<List<MovieEntity>> {
        return movieDao.getMovieList()
    }
}