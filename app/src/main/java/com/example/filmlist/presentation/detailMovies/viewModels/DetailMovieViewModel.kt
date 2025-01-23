package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.GetMovieinfoUseCase
import com.example.filmlist.domain.usecases.LoadFavMovieToDbUseCase
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieinfoUseCase: GetMovieinfoUseCase,
    private val loadMovieToDb: LoadFavMovieToDbUseCase
) : ViewModel() {


    private val _movieInfoState = mutableStateOf(
        InfoMovieState(
            id = "",
            movieEntity = Movie(0, "", "", "", "", 0.0.toString()),
            isFavorite = false
        )
    )
    val movieInfoState = _movieInfoState

    fun send(event: MovieInfoEvent) {
        when (event) {
            is MovieInfoEvent.getMovieInfo -> getMovieInfoById(event.newId)
            is MovieInfoEvent.addMovieToFavorite -> addMovieToFav()
        }
    }

    private fun addMovieToFav() {
        Log.d("Movie", "addMovieToFav: ${_movieInfoState.value.movieEntity}")
        viewModelScope.launch {
            loadMovieToDb.loadFavMovieToDb(_movieInfoState.value.movieEntity)
            _movieInfoState.value = _movieInfoState.value.copy(
                isFavorite = true
            )
        }
    }

    private fun getMovieInfoById(id: String) {
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
            } catch (e: Exception) {
                Log.d("Movie", "getMovieInfoById: $e")
            }
        }

    }
}