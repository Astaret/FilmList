package com.example.filmlist.presentation.searchMovies.viewModels

import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.load_useCases.GetName
import com.example.filmlist.domain.usecases.load_useCases.LoadDataFromSearchUseCase
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.states.SearchState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : BasedViewModel<SearchState, SearchEvents>(SearchState()) {


    override fun send(event: SearchEvents) {
        when (event) {
            is SearchEvents.SearchChange -> onSearchQueryChange(event.newSearch)
        }
    }

    private fun onSearchQueryChange(newQuery: String) {
        setState {
            copy(searchQuery = newQuery)
        }
    }

    private fun loadDataFromSearch(query: String) {
        launchInScope {
            loadDataFromSearchUseCase(GetName(query))
                .collect { outListMovie ->
                    val movies = outListMovie.movieList
                    setState {
                        copy(
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
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        launchInScope {
            state
                .debounce(300)
                .distinctUntilChanged()
                .collect { state ->
                    if (state.searchQuery.isNotEmpty()) {
                        loadDataFromSearch(state.searchQuery)
                    } else {
                        setState {
                            copy(searchResult = emptyList())
                        }
                    }
                }
        }
    }
}