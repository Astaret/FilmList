package com.example.filmlist.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.GetMovieinfoUseCase
import com.example.filmlist.presentation.events.MovieInfoEvent
import com.example.filmlist.presentation.events.getMovieInfo
import com.example.filmlist.presentation.states.InfoMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieinfoUseCase: GetMovieinfoUseCase
) :ViewModel() {


    private val _movieInfoState = mutableStateOf(InfoMovieState(
        id = "",
        movieEntity = MovieEntity(0, "", "", "", "", 0.0.toString())
    ))
    val movieInfoState = _movieInfoState

    fun send(event: MovieInfoEvent){
        when(event){
            is getMovieInfo -> getMovieInfoById(event.newId)
        }
    }



    private fun getMovieInfoById(id: String){
        Log.d("Movie", "getMovieInfoById: $id")
        viewModelScope.launch {
            try {
                val movieInfo = getMovieinfoUseCase.getMovieInfo(id.toInt())
                Log.d("Movie", "getMovieInfoById: $movieInfo")
                if (movieInfo != null) {
                    _movieInfoState.value = _movieInfoState.value.copy(
                        movieEntity = movieInfo
                    )
                } else {
                    Log.d("Movie", "Movie info not found")
                }
            } catch (e: Exception){
                Log.d("Movie", "getMovieInfoById: $e")
            }
        }

    }
}