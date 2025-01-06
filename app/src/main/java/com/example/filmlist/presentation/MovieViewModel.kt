package com.example.filmlist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.filmlist.data.localDb.MovieDatabase
import com.example.filmlist.data.webDb.api.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MovieViewModel(application: Application):AndroidViewModel(application) {

    private val db = MovieDatabase.getInstance(application)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    init {
        loadData()
    }

    fun loadData(){
        coroutineScope.launch {
            Log.d("Movie", "onCreate: coroutine")
            try {
                Log.d("Movie", "onCreate: try")
                val authToken = AUTH_TOKEN
                val response = ApiFactory.api.getTopRatedMovies(authToken)
                with(response){
                    if (this.MovieList.isNotEmpty())
                        response.MovieList.forEach {
                            Log.d("Movie", "onCreateView: $it")
                        }
                    else {
                        Log.d("Movie", "onCreateView: error/empty movie list")
                    }
                    db.movieInfoDao().insertMovieList(this.MovieList)
                    Log.d("Movie", "loadData: SUCCES!!!")

                }
            } catch (e: Exception) {
                Log.e("Movie", "onCreateView: Exception occurred", e)
            }
        }
    }

    companion object{
        private const val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmYmFiODI3NGU2NDYwZTQ2NDg0OGMxOGY1MDRiNWNjMyIsIm5iZiI6MTczNjE2NTAwOS4xODksInN1YiI6IjY3N2JjNjkxNmQ3Y2EwMGU3ODcyZDgwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w-kULlqKEqsWzTagUtFDEwELEtEMS491V5-S7eDO5TI"
    }
}