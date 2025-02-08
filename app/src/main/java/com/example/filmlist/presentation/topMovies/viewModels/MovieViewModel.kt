package com.example.filmlist.presentation.topMovies.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.filmlist.domain.usecases.get_useCases.GetTotalPagesUseCase
import com.example.filmlist.domain.usecases.get_useCases.Params
import com.example.filmlist.domain.usecases.load_useCases.LoadDataUseCase
import com.example.filmlist.domain.usecases.load_useCases.getPage
import com.example.filmlist.presentation.topMovies.states.TopMovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.events.PagingEvents
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getTotalPagesUseCase: GetTotalPagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BasedViewModel<TopMovieState, PagingEvents>(TopMovieState()) {

    private var page: Int
        get() = savedStateHandle["currentPage"] ?: 1
        set(value) {
            savedStateHandle["currentPage"] = value
        }


    override fun handleEvent(event: PagingEvents): TopMovieState {
        return when (event) {
            is PagingEvents.loadingData -> loadData(page)
            is PagingEvents.loadingNextPage -> loadNextPage()
            is PagingEvents.loadingTotalPages -> loadPage()
        }
    }

    private fun loadPage(): TopMovieState{
        handleOperation(
            operation = {getTotalPagesUseCase(Params)},
            onSuccess = { state.value.copy(totalPages = it.pages, isLoading = LoadingState.Succes) }
        )
        return state.value
    }

    private fun loadData(page: Int): TopMovieState {
        handleOperation(
            operation = {loadDataUseCase(getPage(page))},
            onSuccess = {
                val newList = it.movieList
                savedStateHandle["movieList"] = state.value.movieList
                savedStateHandle["currentPage"] = page
                savedStateHandle["totalPages"] = state.value.totalPages
                Log.d("Movie", "loadData: SUCCES ${state.value.movieList}")
                state.value.copy(
                    movieList = (state.value.movieList + newList).distinctBy { it.id },
                    currentPage = page,
                    totalPages = state.value.totalPages,
                    isLoading = LoadingState.Succes
                )
            }
        )
        return state.value
    }

    private fun loadNextPage():TopMovieState {
        if (state.value.currentPage < state.value.totalPages) {
            val page = state.value.currentPage + 1
            loadData(page)
        }
        return state.value
    }
}