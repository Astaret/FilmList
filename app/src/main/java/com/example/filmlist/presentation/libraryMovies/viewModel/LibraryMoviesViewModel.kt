package com.example.filmlist.presentation.libraryMovies.viewModel

import com.example.domain.states.ListMovieState
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.states.LibraryState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.domain.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase:GetMovieListFromBdUseCase
) : BasedViewModel<LibraryState, LibraryEvent>(LibraryState()) {



    override fun handleEvent(event: LibraryEvent): LibraryState {
        return when (event) {
            is LibraryEvent.ShowAllBoughtMovies -> showAllBought()
        }
    }

    private fun showAllBought():LibraryState {
        handleOperation(
            operation = {
                getMovieListFromBdUseCase(
                    getListMovieState(ListMovieState.ISBOUGHT)
                )
            },
            onError = { state.value.copy(isLoading = com.example.domain.states.LoadingState.Error) },
            onSuccess = {
                state.value.copy(
                    movieList = it.listMovies,
                    empty = it.listMovies.isEmpty(),
                    isLoading = LoadingState.Succes
                )
            }
        )
        return state.value
    }
}