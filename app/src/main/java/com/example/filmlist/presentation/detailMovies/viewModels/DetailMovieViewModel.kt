package com.example.filmlist.presentation.detailMovies.viewModels

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AtomicReference
import com.example.domain.states.EntityState
import com.example.domain.states.LoadingState
import com.example.domain.states.MovieState
import com.example.domain.states.StatusMovie
import com.example.domain.usecases.get_useCases.GetId
import com.example.domain.usecases.get_useCases.GetIdForInfo
import com.example.domain.usecases.get_useCases.GetMovieIdFromBdUseCase
import com.example.domain.usecases.get_useCases.GetMovieInfoUseCase
import com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.InfoMovieState
import com.example.filmlist.presentation.toMovieStatus
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : BasedViewModel<InfoMovieState, MovieInfoEvent>() {

    override val cachedScreenState: AtomicReference<InfoMovieState> = AtomicReference(InfoMovieState())

    override fun handleEvent(event: MovieInfoEvent): Flow<InfoMovieState> {
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
    }

    private fun isMovieInBd(id: Int): InfoMovieState {
        handleOperation(
            operation = { getMovieIdFromBdUseCase(GetId(id)) },
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

    private fun addMovieToDataBaseList(movieState:MovieState): InfoMovieState {
        Log.d("Movie", "addMovieToFav: $movieState")
        handleOperation(
            operation = { putMovieToDbUseCase(
                getMovieInfo(
                    state.value.movieEntity,
                    movieState
                )
            ) },
            onSuccess = { state.value.copy(statusMovie = movieState.toMovieStatus()) }
        )
        return state
    }

    private fun deleteMovieFromDataBaseList(): InfoMovieState {
        handleOperation(
            operation = {
                putMovieToDbUseCase(
                    getMovieInfo(
                        state.value.movieEntity,
                        MovieState.EMPTY
                    )
                )
            },
            onSuccess = { state.value.copy(statusMovie =MovieState.EMPTY.toMovieStatus()) }
        )
        return state.value
    }


    private suspend fun getMovieInfoById(id: String) = handleOperation(
            operation = { getMovieInfoUseCase(
               GetIdForInfo(
                    id.toInt()
                )
            ) },
            onSuccess = {
                InfoMovieState(
                    id = it.movie.id.toString(),
                    movieEntity = it.movie,
                    isLoading = LoadingState.Succes
                )
            }
        )


    private suspend fun getQrCode(id: String): Flow<State> {
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

        return flowState(cachedScreenState.get().copy(qrCode = bitmap))
    }
}