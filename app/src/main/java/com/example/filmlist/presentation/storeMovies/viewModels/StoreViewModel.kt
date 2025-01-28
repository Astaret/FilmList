package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.filmlist.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.domain.usecases.load_useCases.PutMovieToDbUseCase
import com.example.filmlist.domain.usecases.load_useCases.getMovieInfo
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase
) : BasedViewModel<StoreMovState, PurchaseEvent>(StoreMovState()) {

    private val _storeState = MutableSharedFlow<StoreMovState>()
    val storeState: SharedFlow<StoreMovState> = _storeState

    override fun send(event: PurchaseEvent) {
        when (event) {
            is PurchaseEvent.ShowAllPurchases -> showAllMoviesInStore()
            is PurchaseEvent.BuyMovie -> buyMovieFun()
        }
    }

    private fun buyMovieFun() {
        launchInScope {
            getMovieListFromBdUseCase(getListMovieState(ListMovieState.INSTORE))
                .collect { result ->
                    val remainingMovies = result.listMovies

                    remainingMovies.forEach {
                        putMovieToDbUseCase(getMovieInfo(it, MovieState.ISBOUGHT)).collect{}
                        Log.d("Movie", "buyMovieFun: BOUGHT ${it.title}")
                    }

                    setState {
                        copy(
                            movieList = emptyList(),
                            totalPrice = 0.0,
                            empty = true
                        )
                    }
                }
        }
    }

    private fun showAllMoviesInStore() {
        launchInScope {
            val updatedMovieList = getMovieListFromBdUseCase(
                getListMovieState(
                    ListMovieState.INSTORE
                )
            ).first().listMovies.map { movie ->
                movie.copy(price = movie.rating.toFloat() * 550.20f)
            }
            Log.d("Movie", "showAllMoviesInStore: $updatedMovieList")
            val totalSum = updatedMovieList.sumOf { it.price?.toDouble() ?: 0.0 }
            setState {
                copy(
                    movieList = updatedMovieList,
                    totalPrice = totalSum,
                    empty = updatedMovieList.isEmpty()
                )
            }
            Log.d("Movie", "showAllMoviesInStore: ${_storeState}")

        }
    }

}