package com.example.filmlist.presentation.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.LoadDataFromSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val loadDataFromSearchUseCase: LoadDataFromSearchUseCase,
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movieList: StateFlow<List<MovieEntity>> get() = _movieList

    var searchQuery by mutableStateOf("")
        private set

    val searchResult: StateFlow<List<MovieEntity>> =
        snapshotFlow { searchQuery }
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

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

}