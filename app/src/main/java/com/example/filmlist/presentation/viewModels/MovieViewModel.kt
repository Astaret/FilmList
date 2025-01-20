package com.example.filmlist.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.GetTotalPagesUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import com.example.filmlist.presentation.events.PagingEvents
import com.example.filmlist.presentation.events.loadingData
import com.example.filmlist.presentation.events.loadingNextPage
import com.example.filmlist.presentation.states.TopMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val getTotalPagesUseCase: GetTotalPagesUseCase
) : ViewModel() {

    init {
        loadData(1)
    }

    private val _moviesState = MutableStateFlow(
        TopMovieState(
            movieList = emptyList(),
            currentPage = 1,
            totalPages = 2
        )
    )
    val movieState = _moviesState

    fun send(event: PagingEvents) {
        when (event) {
            is loadingData -> {
                loadData(movieState.value.currentPage + 1)
            }
            is loadingNextPage ->{
                loadNextPage()
            }
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
                Log.d("Movie", "loadData -> ${_moviesState.value.movieList} ")
            } catch (e: Exception){
                Log.d("Movie", "loadData: FAILED $e")
            }
            Log.d("Movie", "loadData: SUCCES!")
        }
    }
    private fun loadNextPage() {
        Log.d("Movie", "loadNextPage: ${_moviesState.value.currentPage + 1}")
        if (_moviesState.value.currentPage < _moviesState.value.totalPages) {
            loadData(_moviesState.value.currentPage + 1)
        }
    }
}