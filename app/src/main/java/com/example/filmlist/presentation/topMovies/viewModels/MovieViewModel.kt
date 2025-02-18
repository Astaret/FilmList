package com.example.filmlist.presentation.topMovies.viewModels

import android.util.Log
import androidx.lifecycle.AtomicReference
import androidx.lifecycle.SavedStateHandle
import com.example.domain.usecases.get_useCases.GetTotalPagesUseCase
import com.example.domain.usecases.get_useCases.Params
import com.example.domain.usecases.load_useCases.LoadDataUseCase
import com.example.domain.usecases.load_useCases.getPage
import com.example.filmlist.presentation.topMovies.states.TopMovieState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.events.PagingEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getTotalPagesUseCase: GetTotalPagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BasedViewModel<TopMovieState, PagingEvents>() {


    override val cachedScreenState: AtomicReference<TopMovieState> =
        AtomicReference(TopMovieState())

    private var page: Int
        get() = savedStateHandle["currentPage"] ?: 1
        set(value) {
            savedStateHandle["currentPage"] = value
        }


    override suspend fun handleEvent(event: PagingEvents): Flow<TopMovieState> {
        return when (event) {
            is PagingEvents.loadingData -> loadData(page)
            is PagingEvents.loadingNextPage -> loadNextPage()
            is PagingEvents.loadingTotalPages -> loadPage()
        }
    }

    private suspend fun loadPage(): Flow<TopMovieState> = handleOperation(
        operation = { getTotalPagesUseCase(Params) },
        onSuccess = { cachedScreenState.get().copy(totalPages = it.pages) }
    )

    private suspend fun loadData(page: Int): Flow<TopMovieState> = handleOperation(
        operation = { loadDataUseCase(getPage(page)) },
        onSuccess = {
            val newList = it.movieList
            savedStateHandle["movieList"] = cachedScreenState.get().movieList
            savedStateHandle["currentPage"] = page
            savedStateHandle["totalPages"] = cachedScreenState.get().totalPages
            Log.d("Movie", "loadData: SUCCES ${cachedScreenState.get().movieList}")
            cachedScreenState.get().copy(
                movieList = (cachedScreenState.get().movieList + newList).distinctBy { it.id },
                currentPage = page,
                totalPages = cachedScreenState.get().totalPages
            )
        }
    )

    private suspend fun loadNextPage(): Flow<TopMovieState> {
        val currentPage = cachedScreenState.get().currentPage
        val totalPages = cachedScreenState.get().totalPages

        Log.d("Movie", "loadNextPage: ${cachedScreenState.get().totalPages} ")
        Log.d("Movie", "loadNextPage: ${cachedScreenState.get().currentPage} ")
        if (currentPage < totalPages) {
            return loadData(currentPage + 1)
        } else {
            return flow { cachedScreenState.get() }
        }
    }
}