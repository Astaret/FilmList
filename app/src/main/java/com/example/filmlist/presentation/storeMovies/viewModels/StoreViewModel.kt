package com.example.filmlist.presentation.storeMovies.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetStoreMovieUseCase
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getStoreMovieUseCase: GetStoreMovieUseCase
):ViewModel() {

    private val _storeState = MutableStateFlow(StoreMovState())
    val storeState: StateFlow<StoreMovState> = _storeState

    fun send(event: PurchaseEvent){
        when(event){
            PurchaseEvent.showAllPurchases -> showAllMoviesInStore()
        }
    }


    private fun showAllMoviesInStore(){
        viewModelScope.launch {
            val updatedMovieList = getStoreMovieUseCase.getStoreMovie()
            Log.d("Movie", "showAllMoviesInStore: $updatedMovieList")
            if (updatedMovieList.isNotEmpty()){
                _storeState.value = _storeState.value.copy(
                    movieList = updatedMovieList,
                    empty = false
                )
                Log.d("Movie", "showAllMoviesInStore: ${_storeState.value}")

            } else{
                _storeState.value = _storeState.value.copy(
                    empty = true
                )
            }
        }
    }

}