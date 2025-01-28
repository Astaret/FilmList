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
):BasedViewModel<LibraryState, LibraryEvent>(LibraryState()) {


    override fun send(event: LibraryEvent){
        when (event){
            is LibraryEvent.ShowAllBoughtMovies -> showAllBought()
        }
    }

    private fun showAllBought(){
        launchInScope {
            val updatedMovieList = getMovieListFromBdUseCase(
                getListMovieState(ListMovieState.ISBOUGHT)
            ).first().listMovies
            Log.d("Movie", "showAllBoughtMovies: $updatedMovieList")
            setState {
                copy(
                    movieList = updatedMovieList,
                    empty = updatedMovieList.isNullOrEmpty()
                )
            }
            Log.d("Movie", "showAllBought: ${state.value}")

        }
    }
}