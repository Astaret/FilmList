package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.filmlist.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.filmlist.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState
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

    private fun putMovieToDb(movie: Movie, states: MovieState) {
        handleOperation(
            operation = {
                putMovieToDbUseCase(getMovieInfo(movie, states))
            },
            onSuccess = { state.value.copy() }
        )
    }

    private fun buyMovieFun(): StoreMovState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(getListMovieState(ListMovieState.INSTORE))
            },
            onSuccess = {
                val remainingMovies = it.listMovies

                remainingMovies.forEach {
                    putMovieToDb(it, MovieState.ISBOUGHT)
                    Log.d("Movie", "buyMovieFun: BOUGHT ${it.title}")
                }

                state.value.copy(
                    movieList = emptyList(),
                    totalPrice = 0.0,
                    empty = true,
                    isLoading = LoadingState.Succes
                )
            }
        )
        return state.value
    }

    private fun showAllMoviesInStore():StoreMovState {
        handleOperation(
            operation = {getMovieListFromBdUseCase(getListMovieState(ListMovieState.INSTORE))},
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
                    isLoading = LoadingState.Succes
                )
            }
        )
        return state.value
    }

}