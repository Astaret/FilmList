package com.example.filmlist.presentation.topMovies.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.get_useCases.GetTotalPagesUseCase
import com.example.filmlist.domain.usecases.get_useCases.Params
import com.example.filmlist.domain.usecases.load_useCases.LoadDataUseCase
import com.example.filmlist.domain.usecases.load_useCases.getPage
import com.example.filmlist.presentation.topMovies.states.TopMovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.events.PagingEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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


    override fun send(event: PagingEvents) {
        when (event) {
            is PagingEvents.loadingData -> loadData(page)
            is PagingEvents.loadingNextPage -> loadNextPage()
        }
    }

    private fun loadData(page: Int) {
        launchInScope {
            try {
                loadDataUseCase(getPage(page)).collect { newMovies ->
                    val totalPages = getTotalPagesUseCase(Params(Unit)).first().pages
                    val newList = newMovies.movieList
                    setState {
                        copy(
                            movieList = (this.movieList + newList).distinctBy { it.id },
                            currentPage = page,
                            totalPages = totalPages
                        )
                    }

                    savedStateHandle["movieList"] = state.value.movieList
                    savedStateHandle["currentPage"] = page
                    savedStateHandle["totalPages"] = totalPages
                    Log.d("Movie", "loadData -> ${state.value.movieList.map { it.title }} ")
                }
            } catch (e: Exception) {
                Log.d("Movie", "loadData: FAILED $e")
            }
            Log.d("Movie", "loadData: SUCCESS!")
        }
    }

    private fun loadNextPage() {
        if (state.value.currentPage < state.value.totalPages) {
            page += 1
            loadData(page)
        }
    }
}