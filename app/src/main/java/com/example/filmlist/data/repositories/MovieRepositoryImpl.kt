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
    //TODO найди способ обойтись без этого поля

    override suspend fun loadData(token: String) {
        Log.d("Movie", "onCreate: coroutine")
        try {
            Log.d("Movie", "onCreate: try")
            val response = ApiFactory.api.getTopRatedMovies(token)
            //TODO сделай так чтобы apiService инжектился в конструктор репозитория, как movieDao
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
    //TODO а если у тебя будет 10, или 100 функций? Каждую отдельно оборачивать в свой try-catch?
    //TODO придумай как этого избежать

    override fun getMovieInfoList(): LiveData<List<MovieEntity>> {
        return movieDao.getMovieList()
    }
}