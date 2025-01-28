package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.get_useCases.GetId
import com.example.filmlist.domain.usecases.get_useCases.GetIdForInfo
import com.example.filmlist.domain.usecases.get_useCases.GetMovieIdFromBdUseCase
import com.example.filmlist.domain.usecases.get_useCases.GetMovieInfoUseCase
import com.example.filmlist.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.filmlist.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import com.example.filmlist.presentation.detailMovies.states.StatusMovie
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : BasedViewModel<InfoMovieState, MovieInfoEvent>(InfoMovieState()) {

    override fun send(event: MovieInfoEvent) {
        when (event) {
            is MovieInfoEvent.getMovieInfo -> getMovieInfoById(event.newId)
            is MovieInfoEvent.addMovieToDataBase -> addMovieToDataBaseList(event.state)
            is MovieInfoEvent.isMovieInBdCheck -> isMovieInBd(event.id)
        }
    }

    private fun isMovieInBd(id: Int) {
        viewModelScope.launch {
            val movie = getMovieIdFromBdUseCase(GetId(id)).first().movieIdEntity
            if (movie != null) {
                val statusMovie = if (movie.isBought != 1) {
                    if (movie.isFavorite == 1) StatusMovie.FAVORITE else
                        if (movie.isInStore == 1) StatusMovie.INSTORE else StatusMovie.EMPTY
                } else {
                    StatusMovie.BOUGHT
                }
                val movieEnt = getMovieInfoUseCase(GetIdForInfo(id)).first().movie
                setState {
                    copy(
                        id = id.toString(),
                        statusMovie = statusMovie,
                        movieEntity = movieEnt
                    )
                }
            }else{
                val movieEnt = getMovieInfoUseCase(GetIdForInfo(id)).first().movie
                setState {
                    copy(
                        id = id.toString(),
                        statusMovie = StatusMovie.EMPTY,
                        movieEntity = movieEnt
                    )
                }
            }
            Log.d("Movie", "isMovieInBd: ${state.value.statusMovie}")
        }
    }

    private fun addMovieToDataBaseList(movieState: MovieState) {
        Log.d("Movie", "addMovieToFav: $state")
        viewModelScope.launch {
            when (movieState) {
                MovieState.ISFAVORITE -> if (state.value.statusMovie == StatusMovie.EMPTY) {
                    setState {
                        copy(
                            statusMovie = StatusMovie.FAVORITE
                        )
                    }
                    putMovieToDbUseCase(
                        getMovieInfo(
                            state.value.movieEntity,
                            MovieState.ISFAVORITE
                        )
                    ).collect {
                    }
                } else {
                    Log.d("Movie", "addMovieToFav: DELETED")
                    putMovieToDbUseCase(
                        getMovieInfo(
                            state.value.movieEntity, MovieState.EMPTY
                        )
                    ).collect {}
                    setState {
                        copy(
                            statusMovie = StatusMovie.EMPTY
                        )
                    }
                }

                MovieState.ISBOUGHT -> setState { copy(statusMovie = StatusMovie.BOUGHT) }
                MovieState.INSTORE -> if (state.value.statusMovie == StatusMovie.EMPTY) {
                    setState {
                        copy(
                            statusMovie = StatusMovie.INSTORE
                        )
                    }
                    putMovieToDbUseCase(
                        getMovieInfo(
                            movie = state.value.movieEntity,
                            movieState = MovieState.INSTORE
                        )
                    ).collect {}
                } else {
                    Log.d("Movie", "addMovieToStore: DELETED")
                    putMovieToDbUseCase(
                        getMovieInfo(state.value.movieEntity, MovieState.EMPTY)
                    ).collect {}
                    setState {
                        copy(
                            statusMovie = StatusMovie.EMPTY
                        )
                    }

                }

                MovieState.EMPTY -> setState { copy(statusMovie = StatusMovie.EMPTY) }
            }

        }
    }


    private fun getMovieInfoById(id: String) {
        Log.d("Movie", "getMovieInfoById: $id")
        if (id.isNullOrEmpty()) {
            println("Error $id is empty or null")
        } else {
            viewModelScope.launch {
                val movieId = id.toInt()
                val movieInfo = getMovieInfoUseCase(GetIdForInfo(movieId)).first().movie
                Log.d("Movie", "getMovieInfoById: ${movieInfo.title}")
                if (movieInfo != null) {
                    setState {
                        copy(
                            movieEntity = movieInfo
                        )
                    }
                } else {
                    Log.d("Movie", "Movie info not found")
                }
            }
        }
    }
}