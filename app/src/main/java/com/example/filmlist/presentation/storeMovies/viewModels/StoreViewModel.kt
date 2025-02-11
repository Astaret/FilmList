package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import com.example.domain.models.Movie
import com.example.domain.states.ListMovieState
import com.example.domain.states.MovieState
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase,
    private val putMovieToDbUseCase: com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
) : BasedViewModel<StoreMovState, PurchaseEvent>(StoreMovState()) {

    private val _storeState = MutableSharedFlow<StoreMovState>()
    val storeState: SharedFlow<StoreMovState> = _storeState


    override fun handleEvent(event: PurchaseEvent): StoreMovState {
        return when (event) {
            is PurchaseEvent.ShowAllPurchases -> showAllMoviesInStore()
            is PurchaseEvent.BuyMovie -> buyMovieFun()
        }
    }

    private fun putMovieToDb(movie: com.example.domain.models.Movie, states: com.example.domain.states.MovieState) {
        handleOperation(
            operation = {
                putMovieToDbUseCase(
                    com.example.domain.usecases.load_useCases.getMovieInfo(
                        movie,
                        states
                    )
                )
            },
            onError = { state.value.copy(isLoading = com.example.domain.states.LoadingState.Error) },
            onSuccess = { state.value.copy() }
        )
    }

    private fun buyMovieFun(): StoreMovState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    com.example.domain.usecases.get_useCases.getListMovieState(
                        com.example.domain.states.ListMovieState.INSTORE
                    )
                )
            },
            onError = { state.value.copy(isLoading = com.example.domain.states.LoadingState.Error) },
            onSuccess = {
                val remainingMovies = it.listMovies

                remainingMovies.forEach {
                    putMovieToDb(it, com.example.domain.states.MovieState.ISBOUGHT)
                    Log.d("Movie", "buyMovieFun: BOUGHT ${it.title}")
                }

                state.value.copy(
                    movieList = emptyList(),
                    totalPrice = 0.0,
                    empty = true,
                    isLoading = com.example.domain.states.LoadingState.Succes
                )
            }
        )
        return state.value
    }

    private fun showAllMoviesInStore():StoreMovState {
        handleOperation(
            operation = {getMovieListFromBdUseCase(
                com.example.domain.usecases.get_useCases.getListMovieState(
                    com.example.domain.states.ListMovieState.INSTORE
                )
            )},
            onError = { state.value.copy(isLoading = com.example.domain.states.LoadingState.Error) },
            onSuccess = { movie ->
                val listBoughtMovies = movie.listMovies.map {movie ->
                    movie.copy(price = movie.rating.toFloat() * 550.20f)
                }
                Log.d("Movie", "showAllMoviesInStore: $listBoughtMovies")
                val totalSum = listBoughtMovies.sumOf { it.price.toDouble()}
                state.value.copy(
                    movieList = listBoughtMovies,
                    totalPrice = totalSum,
                    empty = listBoughtMovies.isEmpty(),
                    isLoading = com.example.domain.states.LoadingState.Succes
                )
            }
        )
        return state.value
    }

}