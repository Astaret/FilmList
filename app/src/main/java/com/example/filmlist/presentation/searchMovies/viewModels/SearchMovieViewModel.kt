package com.example.filmlist.presentation.searchMovies.viewModels

import androidx.lifecycle.AtomicReference
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.load_useCases.GetName
import com.example.domain.usecases.load_useCases.LoadDataFromSearchUseCase
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.states.SearchState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : BasedViewModel<SearchState, SearchEvents>() {

    override val cachedScreenState: AtomicReference<SearchState> = AtomicReference(SearchState())

    override suspend fun handleEvent(event: SearchEvents): Flow<SearchState> {
        return when (event) {
            is SearchEvents.SearchChange -> onSearchQueryChange(event.newSearch)
        }
    }

    private fun onSearchQueryChange(newQuery: String): Flow<SearchState> {
        return flow { emit(cachedScreenState.updateAndGet { it.copy(searchQuery = newQuery) }) }
    }

    private fun loadDataFromSearch(query: String) {
        viewModelScope.launch {
            handleOperation(
                operation = { loadDataFromSearchUseCase(GetName(query)) },
                withLoading = false,
                onSuccess = { result ->
                    val movies = result.movieList
                    cachedScreenState.updateAndGet { currentState ->
                        currentState.copy(
                            movieList = movies,
                            searchResult = if (query.isNotBlank()) {
                                movies.filter { it.title.contains(query, ignoreCase = true) }
                            } else {
                                movies
                            }
                        )
                    }
                }
            )
                .flowOn(Dispatchers.IO)
                .collect {}
        }
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .debounce(300)
                .distinctUntilChanged()
                .collect { currentState ->
                    if (currentState is SearchState) {
                        if (currentState.searchQuery.isNotEmpty()) {
                            loadDataFromSearch(currentState.searchQuery)
                        } else {
                            cachedScreenState.updateAndGet { it.copy(searchResult = emptyList()) }
                        }
                    }
                }
        }
    }
}