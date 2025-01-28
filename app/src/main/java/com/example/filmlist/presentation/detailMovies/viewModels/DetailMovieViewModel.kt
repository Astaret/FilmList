package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import com.example.filmlist.data.local.db.EntityState
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
        launchInScope {
            val movie = getMovieIdFromBdUseCase(GetId(id)).first().movieIdEntity
            if (movie != null) {
                Log.d("Movie", "isMovieInBd: ${movie.entityState} ${movie.id}")
                val statusMovie = if (movie.entityState != EntityState.ISBOUGHT) {
                    if (movie.entityState == EntityState.ISFAVORITE)
                        StatusMovie.FAVORITE
                    else
                        if (movie.entityState == EntityState.INSTORE)
                            StatusMovie.INSTORE
                        else StatusMovie.EMPTY
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
            } else {
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
        Log.d("Movie", "addMovieToFav: $movieState")
        launchInScope {
            handleOperation(
                operation = {
                    when (movieState) {
                        MovieState.ISFAVORITE -> putMovieToDbUseCase(
                            getMovieInfo(
                                state.value.movieEntity,
                                MovieState.ISFAVORITE
                            )
                        )

                        MovieState.ISBOUGHT -> putMovieToDbUseCase(
                            getMovieInfo(
                                state.value.movieEntity,
                                MovieState.ISBOUGHT
                            )
                        )

                        MovieState.INSTORE -> putMovieToDbUseCase(
                            getMovieInfo(
                                state.value.movieEntity,
                                MovieState.INSTORE
                            )
                        )

                        MovieState.EMPTY -> putMovieToDbUseCase(
                            getMovieInfo(
                                state.value.movieEntity,
                                MovieState.EMPTY
                            )
                        )
                    }
                },
                onSuccess = {
                    val status = when (movieState) {
                        MovieState.ISFAVORITE -> StatusMovie.FAVORITE
                        MovieState.ISBOUGHT -> StatusMovie.BOUGHT
                        MovieState.INSTORE -> StatusMovie.INSTORE
                        MovieState.EMPTY -> StatusMovie.EMPTY
                    }
                    setState { copy(statusMovie = status) }
                }
            )
        }
    }


    private fun getMovieInfoById(id: String) {
        Log.d("Movie", "getMovieInfoById: $id")

        if (id.isNullOrEmpty()) {
            println("Error $id is empty or null")
        } else {
            launchInScope {
                handleOperation(
                    operation = {
                        getMovieInfoUseCase(GetIdForInfo(id.toInt()))
                    },
                    onSuccess = {
                        if (it != null) {
                            setState {
                                copy(
                                    movieEntity = it.movie
                                )
                            }
                        } else {
                            Log.d("Movie", "Movie info not found")
                        }
                    }
                )
            }
        }
    }
}