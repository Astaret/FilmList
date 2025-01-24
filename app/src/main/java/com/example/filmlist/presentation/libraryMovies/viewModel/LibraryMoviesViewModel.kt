package com.example.filmlist.presentation.libraryMovies.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetUseCase.GetBoughtMoviesUseCase
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.states.LibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryMoviesViewModel @Inject constructor(
    private val getBoughtMoviesUseCase: GetBoughtMoviesUseCase
):ViewModel() {
    private val _librState = MutableStateFlow(LibraryState())
    val librState: StateFlow<LibraryState> = _librState


    fun send(event: LibraryEvent){
        when (event){
            is LibraryEvent.ShowAllBoughtMovies -> showAllBought()
        }
    }

    private fun showAllBought(){
        viewModelScope.launch {
            val updatedMovieList = getBoughtMoviesUseCase.getBoughtMovies()
            Log.d("Movie", "showAllBoughtMovies: $updatedMovieList")
            _librState.value = _librState.value.copy(
                movieList = updatedMovieList,
                empty = updatedMovieList.isNullOrEmpty()
            )
            Log.d("Movie", "showAllBought: ${_librState.value}")

        }
    }
}