package com.example.filmlist.presentation.detailMovies.viewModels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
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
                    setState {
                        copy(
                            statusMovie = statusMovie,
                            id = it.movieIdEntity.id.toString(),
                            movieEntity = getMovieInfoById(it.movieIdEntity.id.toString()).movieEntity
                        )}
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
            onSuccess = { setState {  copy(statusMovie = movieState.toMovieStatus())} }
        )
        return state.value
    }

    private fun deleteMovieFromDataBaseList():InfoMovieState{
        handleOperation(
            operation = { putMovieToDbUseCase(getMovieInfo(state.value.movieEntity, MovieState.EMPTY)) },
            onSuccess = { setState {  copy(statusMovie = MovieState.EMPTY.toMovieStatus())} }
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
                onSuccess = {
                    setState { state.value.copy(movieEntity = it.movie) }
                    state.value.copy(movieEntity = it.movie) }
            )
            state.value.copy()
        }
    }


    private fun getQrCode(id:String): InfoMovieState{
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
        setState {
            copy(
                qrCode = bitmap
            )
        }

        return state.value
    }
}