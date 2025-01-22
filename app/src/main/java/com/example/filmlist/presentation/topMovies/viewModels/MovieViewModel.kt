package com.example.filmlist.presentation.topMovies.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetTotalPagesUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import com.example.filmlist.presentation.topMovies.states.TopMovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.events.PagingEvents
import com.example.filmlist.presentation.ui_kit.events.loadingData
import com.example.filmlist.presentation.ui_kit.events.loadingNextPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getTotalPagesUseCase: GetTotalPagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BasedViewModel<TopMovieState, PagingEvents>() {

    private var page: Int
        get() = savedStateHandle["currentPage"] ?: 1
        set(value) {
            savedStateHandle["currentPage"] = value
        }

    private val _moviesState = MutableStateFlow(
        TopMovieState(
            movieList = savedStateHandle["movieList"] ?: emptyList(),
            currentPage = page,
            totalPages = savedStateHandle["totalPages"] ?: 2
        )
    )
    val movieState = _moviesState

    override fun send(event: PagingEvents) {
        when (event) {
            is loadingData -> loadData(page)
            is loadingNextPage -> loadNextPage()
        }
    }

    private fun loadData(page: Int) {
        viewModelScope.launch {
            try {
                val newMovies = loadDataUseCase.loadData(page)
                val totalPages = getTotalPagesUseCase.getTotalPages()
                _moviesState.value = _moviesState.value.copy(
                    movieList = _moviesState.value.movieList + newMovies,
                    currentPage = page,
                    totalPages = totalPages
                )
                savedStateHandle["movieList"] = _moviesState.value.movieList
                savedStateHandle["currentPage"] = page
                savedStateHandle["totalPages"] = totalPages
                Log.d("Movie", "loadData -> ${_moviesState.value.movieList} ")
            } catch (e: Exception) {
                Log.d("Movie", "loadData: FAILED $e")
            }
            Log.d("Movie", "loadData: SUCCESS!")
        }
    }

    private fun loadNextPage() {
        if (_moviesState.value.currentPage < _moviesState.value.totalPages) {
            page += 1
            loadData(page)
        }
    }
}