package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.domain.usecases.LoadDataFromSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList: StateFlow<List<MovieEntity>> get() = _movieList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val searchResult: StateFlow<List<MovieEntity>> =
        _searchQuery
            .debounce(300)
            .combine(_movieList) { searchQuery, movies ->
                when {
                    searchQuery.isNotEmpty() -> movies.filter { movie ->
                        movie.title.contains(searchQuery, ignoreCase = true)
                    }

                    else -> movies
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5000)
            )

    fun loadDataFromSearch(query: String) {
        viewModelScope.launch {
            loadDataFromSearchUseCase.loadDataFromSearch(query)
                .collect { movies ->
                    _movieList.value = movies
                }
        }
    }

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        loadDataFromSearch(query)
                    } else {
                        _movieList.value = emptyList()
                    }
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

}