package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieIdFromBdUseCase
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieinfoUseCase
import com.example.filmlist.domain.usecases.LoadUseCases.LoadFavMovieToDbUseCase
import com.example.filmlist.domain.usecases.LoadUseCases.LoadStoreMovieToDbUseCase
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import com.example.filmlist.presentation.detailMovies.states.StatusMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieinfoUseCase: GetMovieinfoUseCase,
    private val loadFavMovieToDbUseCase: LoadFavMovieToDbUseCase,
    private val loadStoreMovieToDbUseCase: LoadStoreMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : ViewModel() {


    private val _movieInfoState = mutableStateOf(
        InfoMovieState(
            id = "",
            movieEntity = Movie(0, "", "", "", "", 0.0.toString()),
            statusMovie = StatusMovie.EMPTY
        )
    )
    val movieInfoState = _movieInfoState

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
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = statusMovie
                )
            }

        }
    }

    private fun addMovieToFav() {
        Log.d("Movie", "addMovieToFav: ${_movieInfoState.value.movieEntity.movieToMovieEntity()}")
        viewModelScope.launch {
            if (_movieInfoState.value.statusMovie != StatusMovie.FAVORITE ){
                loadFavMovieToDbUseCase.putFavMovieToDb(_movieInfoState.value.movieEntity)
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.FAVORITE
                )
            }else{
                Log.d("Movie", "addMovieToFav: DELETED")
            }
        }
    }

    private fun addMovieToStore() {
        Log.d("Movie", "addMovieToFav: ${_movieInfoState.value.movieEntity}")
        viewModelScope.launch {
            if (_movieInfoState.value.statusMovie != StatusMovie.INSTORE){
                loadStoreMovieToDbUseCase.putStoreMovieToDb(_movieInfoState.value.movieEntity)
                _movieInfoState.value = _movieInfoState.value.copy(
                    statusMovie = StatusMovie.INSTORE
                )
            }else{
                Log.d("Movie", "addMovieToStore: DELETED")
            }
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