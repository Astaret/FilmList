package com.example.filmlist.presentation.detailMovies.viewModels

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AtomicReference
import com.example.domain.types.EntityType
import com.example.domain.types.MovieStatus
import com.example.domain.types.MovieType
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
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase,
    private val getMovieIdFromBdUseCase: GetMovieIdFromBdUseCase
) : BasedViewModel<InfoMovieState, MovieInfoEvent>() {

    override val cachedScreenState: AtomicReference<InfoMovieState> =
        AtomicReference(InfoMovieState())

    override suspend fun handleEvent(event: MovieInfoEvent): Flow<InfoMovieState> {
        return when (event) {
            is MovieInfoEvent.AddMovieToDataBase -> addMovieToDataBaseList(event.state)
            is MovieInfoEvent.DeleteMovieFromDataBase -> deleteMovieFromDataBaseList()
            is MovieInfoEvent.GetAllInfoAboutMovie -> getAllInfoAboutMovie(event.id)
        }
    }

    private suspend fun getAllInfoAboutMovie(id: String): Flow<InfoMovieState> {
        isMovieInBd(id.toInt())
        getMovieInfoById(id)
        getQrCode(id)
        return flow{ cachedScreenState.get() }
    }

    private suspend fun isMovieInBd(id: Int): Flow<InfoMovieState> {
        Log.d("Movie", "isMovieInBd: checked")
        handleOperation(
            operation = { getMovieIdFromBdUseCase(GetId(id)) },
            onSuccess = {
                val moveIdEntity = it.movieIdEntity
                if (moveIdEntity != null) {
                    val movieStatus = if (moveIdEntity.entityType != EntityType.ISBOUGHT) {
                        if (moveIdEntity.entityType == EntityType.ISFAVORITE)
                            MovieStatus.FAVORITE
                        else
                            if (moveIdEntity.entityType == EntityType.INSTORE)
                                MovieStatus.INSTORE
                            else MovieStatus.EMPTY
                    } else {
                        MovieStatus.BOUGHT
                    }
                    Log.d("Movie", "isMovieInBd: $movieStatus $moveIdEntity")
                    cachedScreenState.get().copy(
                        movieStatus = movieStatus,
                        id = moveIdEntity.id.toString(),
                        movieEntity = cachedScreenState.get().movieEntity
                    )
                } else {
                    cachedScreenState.updateAndGet {current ->
                        current.copy(
                            movieStatus = MovieStatus.EMPTY
                        )
                    }
                }
            }
        )
        return flow{ cachedScreenState.get() }
    }

    private suspend fun addMovieToDataBaseList(movieType: MovieType): Flow<InfoMovieState> {
        Log.d("Movie", "addMovieToFav: $movieType")
        cachedScreenState.get().movieEntity?.let { movie ->
            handleOperation(
                operation = {
                    putMovieToDbUseCase(
                        getMovieInfo(
                            movie = movie,
                            movieType = movieType
                        )
                    )
                },
                onSuccess = {
                    cachedScreenState.updateAndGet { current ->
                        current.copy(
                            movieStatus = movieType.toMovieStatus()
                        )
                    }
                }
            )
        }
        return flow{ cachedScreenState.get() }
    }

    private suspend fun deleteMovieFromDataBaseList(): Flow<InfoMovieState> {
        cachedScreenState.get().movieEntity?.let { movie ->
            handleOperation(
                operation = {
                    putMovieToDbUseCase(
                        getMovieInfo(
                            movie = movie,
                            movieType = MovieType.EMPTY
                        )
                    )
                },
                onSuccess = {
                    cachedScreenState.updateAndGet { current ->
                        current.copy(
                            movieStatus = MovieType.EMPTY.toMovieStatus()
                        )
                    }
                }
            )
        }
        return flow{ cachedScreenState.get() }
    }


    private suspend fun getMovieInfoById(id: String) = handleOperation(
        operation = {
            Log.d("Movie", "getMovieInfoById: $id")
            getMovieInfoUseCase(
                GetIdForInfo(
                    id.toInt()
                )
            )
        },
        onSuccess = {
            cachedScreenState.updateAndGet { current ->
                current.copy(
                    id = it.movie.id.toString(),
                    movieEntity = it.movie
                )
            }
        }
    )


    private fun getQrCode(id: String): Flow<State> {
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