package com.example.filmlist.presentation.favoritesMovies.viewModels

import androidx.lifecycle.AtomicReference
import com.example.domain.types.ListMovieType
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
) : BasedViewModel<FavoriteState, FavoriteEvent>() {

    override val cachedScreenState: AtomicReference<FavoriteState> = AtomicReference(FavoriteState())

    override suspend fun handleEvent(event: FavoriteEvent): Flow<FavoriteState> {
        return when (event) {
            is FavoriteEvent.DeleteFromFavorite -> deleteFromFavorite()
            is FavoriteEvent.ShowAllFavorites -> showAllFavorites()
        }
    }

    private suspend fun showAllFavorites(): Flow<FavoriteState> = handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    getListMovieState(ListMovieType.ISFAVORITE)
                )
            },
            onSuccess = {
                cachedScreenState.get().copy(
                    movieList = it.listMovies,
                    empty = it.listMovies.isEmpty()
                )
            }
        )
    private fun deleteFromFavorite(): Flow<FavoriteState> {
        return  flow{ cachedScreenState.get() }
    }

}

