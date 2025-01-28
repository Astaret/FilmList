package com.example.filmlist.presentation.favoritesMovies.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.filmlist.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
) : BasedViewModel<FavoriteState, FavoriteEvent>(FavoriteState()) {

    override fun send(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.DeleteFromFavorite -> deleteFromFavorite()
            is FavoriteEvent.ShowAllFavorites -> showAllFavorites()
        }
    }

    private fun showAllFavorites() {
        Log.d("Movie", "showAllFavorites: start")
        launchInScope {
            Log.d("Movie", "showAllFavorites: continue")
            getMovieListFromBdUseCase(
                getListMovieState(ListMovieState.ISFAVORITE)
            ).collect{
                Log.d("Movie", "showAllFavorites: ${it.listMovies.map { it.title } }")
                setState {
                    copy(
                        movieList = it.listMovies,
                        empty = it.listMovies.isEmpty()
                    )
                }
                Log.d("Movie", "showAllFavorites: ${state.value.movieList.map { it.title }}")
            }
        }

    }
}

private fun deleteFromFavorite() {

}