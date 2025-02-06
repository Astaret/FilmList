package com.example.filmlist.presentation.libraryMovies.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.filmlist.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.states.LibraryState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
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
            onSuccess = {
                state.value.copy(
                    movieList = it.listMovies,
                    empty = it.listMovies.isNullOrEmpty()
                )
            }
        )
        return state.value
    }
}