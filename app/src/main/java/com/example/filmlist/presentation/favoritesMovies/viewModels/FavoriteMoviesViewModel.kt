package com.example.filmlist.presentation.favoritesMovies.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.usecases.GetUseCase.GetMovieListFromBdUseCase
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
) : BasedViewModel<FavoriteState, FavoriteEvent>() {

    private val _favState = MutableStateFlow(FavoriteState())
    val favState: StateFlow<FavoriteState> = _favState


    override fun send(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.DeleteFromFavorite -> deleteFromFavorite()
            is FavoriteEvent.ShowAllFavorites -> showAllFavorites()
        }
    }

    private fun showAllFavorites() {
        viewModelScope.launch {
            val updatedMovieList = getMovieListFromBdUseCase.getMovieListFromBd(
                ListMovieState.ISFAVORITE)
            Log.d("Movie", "showAllFavorites: ${updatedMovieList.map { it.title }}")
            _favState.value = _favState.value.copy(
                movieList = updatedMovieList,
                empty = updatedMovieList.isEmpty()
            )
            Log.d("Movie", "showAllFavorites: ${_favState.value.movieList.map { it.title }}")

        }
    }

    private fun deleteFromFavorite() {

    }
}