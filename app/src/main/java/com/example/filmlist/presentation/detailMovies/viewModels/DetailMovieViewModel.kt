package com.example.filmlist.presentation.detailMovies.viewModels

import android.util.Log
import com.example.filmlist.data.local.db.EntityState
import com.example.filmlist.data.mappers.toMovieStatus
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
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : BasedViewModel<InfoMovieState, MovieInfoEvent>(InfoMovieState()) {


    override fun handleEvent(event: MovieInfoEvent): InfoMovieState {
        return when (event) {
            is MovieInfoEvent.getMovieInfo -> getMovieInfoById(event.newId)
            is MovieInfoEvent.addMovieToDataBase -> addMovieToDataBaseList(event.state)
            is MovieInfoEvent.isMovieInBdCheck -> isMovieInBd(event.id)
        }
    }

    private fun isMovieInBd(id: Int): InfoMovieState {
        handleOperation(
            operation = { getMovieIdFromBdUseCase(GetId(id)) },
            onSuccess = {
                if (it.movieIdEntity != null) {
                    val statusMovie = if (it.movieIdEntity.entityState != EntityState.ISBOUGHT) {
                        if (it.movieIdEntity.entityState == EntityState.ISFAVORITE)
                            StatusMovie.FAVORITE
                        else
                            if (it.movieIdEntity.entityState == EntityState.INSTORE)
                                StatusMovie.INSTORE
                            else StatusMovie.EMPTY
                    } else {
                        StatusMovie.BOUGHT
                    }
                    state.value.copy(statusMovie = statusMovie)
                } else {
                    state.value.copy(statusMovie = StatusMovie.EMPTY)
                }
            }
        )
        return state.value
    }

    private fun addMovieToDataBaseList(movieState: MovieState): InfoMovieState {
        Log.d("Movie", "addMovieToFav: $movieState")
        handleOperation(
            operation = { putMovieToDbUseCase(getMovieInfo(state.value.movieEntity, movieState)) },
            onSuccess = { state.value.copy(statusMovie = movieState.toMovieStatus()) }
        )
        return state.value
    }


    private fun getMovieInfoById(id: String): InfoMovieState {
        Log.d("Movie", "getMovieInfoById: $id")
        return if (id.isNullOrEmpty()) {
            println("Error $id is empty or null")
            state.value
        } else {
            handleOperation(
                operation = { getMovieInfoUseCase(GetIdForInfo(id.toInt())) },
                onSuccess = { state.value.copy(movieEntity = it.movie) }
            )
            state.value
        }
    }

}