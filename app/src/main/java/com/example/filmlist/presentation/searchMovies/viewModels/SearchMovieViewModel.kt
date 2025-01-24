package com.example.filmlist.presentation.searchMovies.viewModels

import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.LoadUseCases.LoadDataFromSearchUseCase
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.states.SearchState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : BasedViewModel<SearchState, SearchEvents>() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    override fun send(event: SearchEvents) {
        when (event) {
            is SearchEvents.SearchChange -> onSearchQueryChange(event.newSearch)
        }
    }

    private fun onSearchQueryChange(newQuery: String) {
        _searchState.value = _searchState.value.copy(
            searchQuery = newQuery
        )
    }

    private fun loadDataFromSearch(query: String) {
        viewModelScope.launch {
            loadDataFromSearchUseCase.loadDataFromSearch(query)
                .collect { movies ->
                    _searchState.value = _searchState.value.copy(
                        movieList = movies,
                        searchResult = if (query.isNotEmpty()) {
                            movies.filter { it.title.contains(query, ignoreCase = true) }
                        } else {
                            movies
                        }
                    )
                }
        }
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchState
                .debounce(300)
                .distinctUntilChanged()
                .collect { state ->
                    if (state.searchQuery.isNotEmpty()) {
                        loadDataFromSearch(state.searchQuery)
                    } else {
                        _searchState.value = state.copy(searchResult = emptyList())
                    }
                }
        }
    }
}