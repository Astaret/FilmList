package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import com.example.domain.entities.Movie
import com.example.domain.types.ListMovieType
import com.example.domain.types.MovieType
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.toBoughtMovies
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase
) : BasedViewModel<StoreMovState, PurchaseEvent>(StoreMovState()) {

    private val _storeState = MutableSharedFlow<StoreMovState>()
    val storeState: SharedFlow<StoreMovState> = _storeState


    override fun handleEvent(event: PurchaseEvent): StoreMovState {
        return when (event) {
            is PurchaseEvent.ShowAllPurchases -> showAllMoviesInStore()
            is PurchaseEvent.BuyMovie -> buyMovieFun()
        }
    }

    private fun putMovieToDb(movie: Movie, states: MovieType) {
        handleOperation(
            operation = {
                putMovieToDbUseCase(
                    getMovieInfo(
                        movie,
                        states
                    )
                )
            },
            onError = {handleError(it) },
            onSuccess = { state.value.copy() }
        )
    }

    private fun buyMovieFun(): StoreMovState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    getListMovieState(
                        ListMovieType.INSTORE
                    )
                )
            },
            onError = {handleError(it) },
            onSuccess = {
                val remainingMovies = it.listMovies

                remainingMovies.forEach {
                    putMovieToDb(it, MovieType.ISBOUGHT)
                    Log.d("Movie", "buyMovieFun: BOUGHT ${it.title}")
                }

                state.value.copy(
                    movieList = emptyList(),
                    totalPrice = 0.0,
                    empty = true
                )
            }
        )
        return state.value
    }

    private fun showAllMoviesInStore(): StoreMovState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    getListMovieState(
                        ListMovieType.INSTORE
                    )
                )
            },
            onError = {handleError(it) },
            onSuccess = { movie ->
                val listBoughtMovies = movie.listMovies.toBoughtMovies()
                Log.d("Movie", "showAllMoviesInStore: $listBoughtMovies")
                val totalSum = listBoughtMovies.sumOf { it.price.toDouble() }
                state.value.copy(
                    movieList = listBoughtMovies,
                    totalPrice = totalSum,
                    empty = listBoughtMovies.isEmpty()
                )
            }
        )
        return state.value
    }

}