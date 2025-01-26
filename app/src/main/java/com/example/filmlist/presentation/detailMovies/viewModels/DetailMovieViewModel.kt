package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieIdFromBdUseCase
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieinfoUseCase
import com.example.filmlist.domain.usecases.LoadUseCases.PutMovieToDbUseCase
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import com.example.filmlist.presentation.detailMovies.states.StatusMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieinfoUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : ViewModel() {

    private val _movieInfoState = MutableStateFlow(InfoMovieState())
    val movieInfoState: MutableStateFlow<InfoMovieState> = _movieInfoState

    fun send(event: MovieInfoEvent) {
        when (event) {
            is MovieInfoEvent.getMovieInfo -> getMovieInfoById(event.newId)
            is MovieInfoEvent.addMovieToFavorite -> addMovieToFav()
            is MovieInfoEvent.addMovieToStore -> addMovieToStore()
            is MovieInfoEvent.isMovieInBdCheck -> isMovieInBd(event.id)
        }
    }

    private fun isMovieInBd(id: Int) {
        viewModelScope.launch {
            val movie = getMovieIdFromBdUseCase.getMovieByIdFromBd(id)
            if (movie != null) {
                val statusMovie = if (movie.isBought != 1){
                    if (movie.isFavorite == 1) StatusMovie.FAVORITE else
                    if (movie.isInStore == 1) StatusMovie.INSTORE else StatusMovie.EMPTY
                }else{
                    StatusMovie.BOUGHT
                }
                _movieInfoState.emit(InfoMovieState(
                    id = id.toString(),
                    statusMovie = statusMovie,
                    movieEntity = getMovieInfoUseCase.getMovieInfo(id)
                ))
            }
            Log.d("Movie", "isMovieInBd: ${_movieInfoState.value.statusMovie}")
        }
    }

    private fun addMovieToFav() {
        Log.d("Movie", "addMovieToFav: $_movieInfoState")
        viewModelScope.launch {
            if (_movieInfoState.value.statusMovie == StatusMovie.EMPTY ){
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.FAVORITE
                )
                putMovieToDbUseCase.putMovieToDb(_movieInfoState.value.movieEntity, MovieState.ISFAVORITE)
            }else{
                Log.d("Movie", "addMovieToFav: DELETED")
                putMovieToDbUseCase.putMovieToDb(_movieInfoState.value.movieEntity, MovieState.EMPTY)
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.EMPTY
                )
            }
        }
    }

    private fun addMovieToStore() {
        Log.d("Movie", "addMovieToFav: ${_movieInfoState.value.movieEntity}")
        viewModelScope.launch {
            if (_movieInfoState.value.statusMovie == StatusMovie.EMPTY){
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.INSTORE
                )
                putMovieToDbUseCase.putMovieToDb(_movieInfoState.value.movieEntity, MovieState.INSTORE)
            }else{
                Log.d("Movie", "addMovieToStore: DELETED")
                putMovieToDbUseCase.putMovieToDb(_movieInfoState.value.movieEntity, MovieState.EMPTY)
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.EMPTY
                )

            }
        }
    }

    private fun getMovieInfoById(id: String) {
        Log.d("Movie", "getMovieInfoById: $id")
        viewModelScope.launch {
            try {
                val movieInfo = getMovieInfoUseCase.getMovieInfo(id.toInt())
                Log.d("Movie", "getMovieInfoById: ${movieInfo.title}")
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