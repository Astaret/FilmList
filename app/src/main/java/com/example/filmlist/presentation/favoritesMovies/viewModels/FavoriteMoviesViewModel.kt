package com.example.filmlist.presentation.favoritesMovies.viewModels

import com.example.domain.types.ListMovieType
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
) : BasedViewModel<FavoriteState, FavoriteEvent>(FavoriteState()) {


    override fun handleEvent(event: FavoriteEvent): FavoriteState {
        return when (event) {
            is FavoriteEvent.DeleteFromFavorite -> deleteFromFavorite()
            is FavoriteEvent.ShowAllFavorites -> showAllFavorites()
        }
    }

    private fun showAllFavorites(): FavoriteState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    getListMovieState(ListMovieType.ISFAVORITE)
                )
            },
            onError = { handleError(it) },
            onSuccess = {
                FavoriteState(
                    movieList = it.listMovies,
                    empty = it.listMovies.isEmpty()
                )
            }
        )
        return state.value
    }
}

private fun deleteFromFavorite(): FavoriteState {
    return FavoriteState()
}