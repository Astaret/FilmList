package com.example.filmlist.data.repositories

import android.util.Log
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.data.mappers.dtoToMovieEntity
import com.example.filmlist.data.mappers.entityToMovie
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieInfoDao,
    private val apiService: ApiService
) : MovieRepository {


    override suspend fun loadData(page:Int): List<Movie> {
        Log.d("Movie", "onCreate: coroutine")
        var data = listOf<Movie>()
        var count = 1
        Log.d("Movie", "onCreate: try")
        with(apiService.getTopRatedMovies(page = page)) {
            movieDao.insertMovieList(
                MovieList.map {
                    Log.d("Movie", "onCreateView: $it $count")
                    count++
                    it.dtoToMovieEntity()
                }
            )
        }
        val flowData = movieDao.getMovieList().first()
        data = flowData.map { it.entityToMovie() }
        Log.d("Movie", "loadData: $data")
        return data
    }

    override fun getMovieInfoList(): Flow<List<MovieEntity>> {
        return movieDao.getMovieList()
    }
}