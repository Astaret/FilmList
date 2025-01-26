package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieListFromBdUseCase
import com.example.filmlist.domain.usecases.LoadUseCases.PutMovieToDbUseCase
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase,
    private val putMovieToDbUseCase: PutMovieToDbUseCase
) : ViewModel() {

    private val _storeState = MutableSharedFlow<StoreMovState>()
    val storeState: SharedFlow<StoreMovState> = _storeState

    fun send(event: PurchaseEvent) {
        when (event) {
            is PurchaseEvent.ShowAllPurchases -> showAllMoviesInStore()
            is PurchaseEvent.BuyMovie -> buyMovieFun()
        }
    }

    private fun buyMovieFun() {
        viewModelScope.launch {
             getMovieListFromBdUseCase.getMovieListFromBd(ListMovieState.INSTORE).forEach {
                putMovieToDbUseCase.putMovieToDb(it, MovieState.ISBOUGHT)
                Log.d("Movie", "buyMovieFun: BOUGHT")
            }
            val remainingMovies = getMovieListFromBdUseCase.getMovieListFromBd(
                ListMovieState.INSTORE)
            val updatedState = StoreMovState(
                movieList = remainingMovies,
                totalPrice = remainingMovies.sumOf { it.price?.toDouble() ?: 0.0 },
                empty = remainingMovies.isEmpty()
            )
            _storeState.emit(updatedState)

        }
    }

    private fun showAllMoviesInStore() {
        viewModelScope.launch {
            val updatedMovieList = getMovieListFromBdUseCase.getMovieListFromBd(
                ListMovieState.INSTORE).map {
                    movie -> movie.copy(price = movie.rating.toFloat() * 550.20f)
                }
            Log.d("Movie", "showAllMoviesInStore: $updatedMovieList")
            val totalSum = updatedMovieList.sumOf { it.price?.toDouble() ?: 0.0 }
            _storeState.emit(StoreMovState(
                movieList = updatedMovieList,
                totalPrice = totalSum,
                empty = updatedMovieList.isEmpty()
            ))
            Log.d("Movie", "showAllMoviesInStore: ${_storeState}")

        }
    }

}