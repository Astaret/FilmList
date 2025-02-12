package com.example.filmlist.presentation.detailMovies.viewModels

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.example.domain.states.EntityState
import com.example.domain.states.MovieState
import com.example.domain.usecases.get_useCases.GetId
import com.example.domain.usecases.get_useCases.GetIdForInfo
import com.example.domain.usecases.get_useCases.GetMovieIdFromBdUseCase
import com.example.domain.usecases.get_useCases.GetMovieInfoUseCase
import com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState
import com.example.domain.states.StatusMovie
import com.example.filmlist.presentation.toMovieStatus
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
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
            is MovieInfoEvent.GetMovieInfo -> getMovieInfoById(event.newId)
            is MovieInfoEvent.AddMovieToDataBase -> addMovieToDataBaseList(event.state)
            is MovieInfoEvent.IsMovieInBdCheck -> isMovieInBd(event.id)
            is MovieInfoEvent.DeleteMovieFromDataBase -> deleteMovieFromDataBaseList()
            is MovieInfoEvent.GetQrCode -> getQrCode(event.id)
            is MovieInfoEvent.GetAllInfoAboutMovie -> getAllInfoAboutMovie(event.id)
        }
    }

    private fun getAllInfoAboutMovie(id: String): InfoMovieState {
        isMovieInBd(id.toInt())
        getMovieInfoById(id)
        getQrCode(id)
        return state.value
    }

    private fun isMovieInBd(id: Int): InfoMovieState {
        handleOperation(
            operation = { getMovieIdFromBdUseCase(GetId(id)) },
            onError = { state.value.copy(isLoading = LoadingState.Error) },
            onSuccess = {
                val moveIdEntity = it.movieIdEntity
                if (moveIdEntity != null) {
                    val statusMovie = if (moveIdEntity.entityState != EntityState.ISBOUGHT) {
                        if (moveIdEntity.entityState == EntityState.ISFAVORITE)
                            StatusMovie.FAVORITE
                        else
                            if (moveIdEntity.entityState == EntityState.INSTORE)
                                StatusMovie.INSTORE
                            else StatusMovie.EMPTY
                    } else {
                        StatusMovie.BOUGHT
                    }
                    state.value.copy(
                        statusMovie = statusMovie,
                        id = moveIdEntity.id.toString(),
                        movieEntity = getMovieInfoById(moveIdEntity.id.toString()).movieEntity
                    )
                } else {
                    state.value.copy(statusMovie = StatusMovie.EMPTY)
                }
            }
        )
        return state.value
    }

    private fun addMovieToDataBaseList(movieState: MovieState): InfoMovieState {
        Log.d("Movie", "addMovieToFav: $movieState")
        state.value.movieEntity?.let {movie ->
            handleOperation(
                operation = {
                    putMovieToDbUseCase(
                        getMovieInfo(
                            movie = movie,
                            movieState = movieState
                        )
                    )
                },
                onError = { state.value.copy(isLoading = LoadingState.Error) },
                onSuccess = { state.value.copy(statusMovie = movieState.toMovieStatus()) }
            )
        }
        return state.value
    }

    private fun deleteMovieFromDataBaseList(): InfoMovieState {
        state.value.movieEntity?.let { movie ->
            handleOperation(
                operation = {
                    putMovieToDbUseCase(
                        getMovieInfo(
                            movie = movie,
                            movieState = MovieState.EMPTY
                        )
                    )
                },
                onError = { state.value.copy(isLoading = LoadingState.Error) },
                onSuccess = { state.value.copy(statusMovie = MovieState.EMPTY.toMovieStatus()) }
            )
        }
        return state.value
    }


    private fun getMovieInfoById(id: String): InfoMovieState {
        Log.d("Movie", "getMovieInfoById: $id")
        handleOperation(
            operation = {
                getMovieInfoUseCase(
                    GetIdForInfo(
                        id.toInt()
                    )
                )
            },
            onError = { state.value.copy(isLoading = LoadingState.Error) },
            onSuccess = {
                InfoMovieState(
                    id = it.movie.id.toString(),
                    movieEntity = it.movie,
                    isLoading = LoadingState.Succes
                )
            }
        )
        return state.value
    }


    private fun getQrCode(id: String): InfoMovieState {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            id, BarcodeFormat.QR_CODE, 512, 512
        )

        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return state.value.copy(qrCode = bitmap)
    }
}