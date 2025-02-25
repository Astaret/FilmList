package com.example.filmlist.presentation.searchMovies.ui.compose.Screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.searchMovies.events.SearchEvents
import com.example.filmlist.presentation.searchMovies.states.SearchState
import com.example.filmlist.presentation.searchMovies.viewModels.SearchMovieViewModel
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.movie_cards.MovieCard
import com.example.myapp.R
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    vm: SearchMovieViewModel = hiltViewModel(),
    navController: NavController
) {
    val currentState by vm.state.collectAsStateWithLifecycle(initialValue = BasedViewModel.State.Loading)


    LaunchedEffect(Unit) {
        vm.receiveEvent(SearchEvents.SearchChange(""))
    }

    BackHandler {
        navController.popBackStack()
    }

    MainContainer(
        state = currentState
    ) {
        if (currentState is SearchState) {
            val searchState = currentState as SearchState

            SearchContent(
                searchQuery = searchState.searchQuery,
                searchResults = searchState.searchResult,
                navController = navController,
                onSearchQueryChange = { vm.receiveEvent(SearchEvents.SearchChange(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchContent(
    searchQuery: String,
    searchResults: List<Movie>,
    navController: NavController,
    onSearchQueryChange: (String) -> Unit
) {
    var isActive by remember { mutableStateOf(false) }
    var localSearchQuery by remember { mutableStateOf(searchQuery) }

    LaunchedEffect(localSearchQuery) {
        snapshotFlow { localSearchQuery }
            .debounce(200)
            .distinctUntilChanged()
            .collect{
                onSearchQueryChange(it)
            }
    }

    Box {
        SearchBar(
            query = localSearchQuery,
            onQueryChange = { newValue -> localSearchQuery = newValue },
            onSearch = {  },
            placeholder = {
                Text(text = stringResource(R.string.search_movies))
            },
            trailingIcon = {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.Back)
                    )
                }
            },
            content = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults.chunked(2)) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            rowItems.forEach { movie ->
                                MovieCard(movie = movie, navController = navController)
                            }
                        }
                    }
                }
            },
            active = isActive,
            onActiveChange = { isActive = it },
            tonalElevation = 0.dp
        )
    }
}