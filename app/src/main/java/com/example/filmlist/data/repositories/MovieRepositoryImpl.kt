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
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : MovieRepository {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override  fun loadData() {
        Log.d("Movie", "onCreate: coroutine")
        coroutineScope.errorHandled {
            Log.d("Movie", "onCreate: try")
            with(apiService.getTopRatedMovies()) {
                movieDao.insertMovieList(
                    MovieList.map {
                        Log.d("Movie", "onCreateView: $it")
                        it.dtoToMovieEntity()
                    }
                )
                Log.d("Movie", "loadData: SUCCES!!!")
            }

        }
    }


    override fun getMovieInfoList(): Flow<List<MovieEntity>> {
        return movieDao.getMovieList()
    }
}