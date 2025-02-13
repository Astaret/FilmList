package com.example.filmlist.presentation.searchMovies.viewModels

import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.load_useCases.GetName
import com.example.domain.usecases.load_useCases.LoadDataFromSearchUseCase
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.states.SearchState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : BasedViewModel<SearchState, SearchEvents>(SearchState()) {


    override fun handleEvent(event: SearchEvents): SearchState {
        return when (event) {
            is SearchEvents.SearchChange -> onSearchQueryChange(event.newSearch)
        }
    }

    private fun onSearchQueryChange(newQuery: String): SearchState {
        setState {
            copy(searchQuery = newQuery, isLoading = LoadingState.Succes)
        }
        return state.value
    }

    private fun loadDataFromSearch(query: String) {
        handleOperation(
            operation = {
                loadDataFromSearchUseCase(
                    GetName(
                        query
                    )
                )
            },
            onError = { state.value.copy(isLoading = LoadingState.Error(it.message)) },
            onSuccess = {
                val movies = it.movieList
                state.value.copy(
                    movieList = movies,
                    searchResult = if (query.isNotEmpty()) {
                        movies.filter { it.title.contains(query, ignoreCase = true) }
                    } else {
                        movies
                    },
                    isLoading = LoadingState.Succes
                )
            }
        )
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .debounce(300)
                .distinctUntilChanged()
                .collect { state ->
                    if (state.searchQuery.isNotEmpty()) {
                        loadDataFromSearch(state.searchQuery)
                    } else {
                        setState {
                            copy(searchResult = emptyList(), isLoading = LoadingState.Succes)
                        }
                    }
                }
        }
    }
}