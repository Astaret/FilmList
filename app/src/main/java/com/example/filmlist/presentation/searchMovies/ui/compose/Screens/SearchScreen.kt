package com.example.filmlist.presentation.searchMovies.ui.compose.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.viewModels.SearchMovieViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.movie_cards.MovieCard
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    vm: SearchMovieViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchState by vm.state.collectAsState()

    MainContainer(
        isLoading = searchState.isLoading
    ) {
        SearchScreen(
            searchQuery = searchState.searchQuery,
            searchResults = searchState.searchResult,
            navController = navController,
            onSearchQueryChange = { vm.receiveEvent(SearchEvents.SearchChange(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    searchQuery: String,
    searchResults: List<Movie>,
    navController: NavController,
    onSearchQueryChange: (String) -> Unit
) {
    Box{
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearch = {},
            placeholder = {
                Text(text = "Search movies")
            },
            trailingIcon = {},
            content = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults.chunked(2)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            it.forEach{
                                MovieCard(movie = it, navController = navController)
                            }
                        }
                    }
                }
            },
            active = true,
            onActiveChange = {},
            tonalElevation = 0.dp
        )
    }
}
