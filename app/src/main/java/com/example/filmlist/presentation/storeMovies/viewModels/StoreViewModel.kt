package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetUseCase.GetStoreMovieUseCase
import com.example.filmlist.domain.usecases.LoadUseCases.LoadBoughtMovieToDbUseCase
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getStoreMovieUseCase: GetStoreMovieUseCase,
    private val loadBoughtMovieToDbUseCase: LoadBoughtMovieToDbUseCase
) : ViewModel() {

    private val _storeState = MutableStateFlow(StoreMovState())
    val storeState: StateFlow<StoreMovState> = _storeState

    fun send(event: PurchaseEvent) {
        when (event) {
            is PurchaseEvent.ShowAllPurchases -> showAllMoviesInStore()
            is PurchaseEvent.BuyMovie -> buyMovieFun()
        }
    }

    private fun buyMovieFun() {
        viewModelScope.launch {
            getStoreMovieUseCase.getStoreMovie().forEach {
                loadBoughtMovieToDbUseCase.saveBoughtMovies(it)
                Log.d("Movie", "buyMovieFun: BOUGHT")
            }

        }
    }

    private fun showAllMoviesInStore() {
        viewModelScope.launch {
            val updatedMovieList = getStoreMovieUseCase.getStoreMovie()
                .map {
                    movie -> movie.copy(price = movie.rating.toFloat() * 550.20f)
                }
            Log.d("Movie", "showAllMoviesInStore: $updatedMovieList")
            val totalSum = updatedMovieList.sumOf { it.price?.toDouble() ?: 0.0 }
            _storeState.value = _storeState.value.copy(
                movieList = updatedMovieList,
                totalPrice = totalSum,
                empty = updatedMovieList.isEmpty()
            )
            Log.d("Movie", "showAllMoviesInStore: ${_storeState.value}")

        }
    }

}