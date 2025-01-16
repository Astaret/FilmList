package com.example.filmlist.presentation.ui.Compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.presentation.viewModels.SearchMovieViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    viewModel: SearchMovieViewModel
) {
    val searchResult by viewModel.searchResult.collectAsState()


    SearchScreen(
        searchQuery = viewModel.searchQuery,
        searchResults = searchResult,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<MovieEntity>,
    onSearchQueryChange: (String) -> Unit
) {
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
                items(
                    count = searchResults.size,
                    key = { index -> searchResults[index].id },
                    itemContent = { index ->
                        val movie = searchResults[index]
                        MovieListItem(movie = movie)
                    }
                )
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
fun MovieListItem(
    movie: MovieEntity,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = movie.title)
        Text(text = movie.rating)
    }
}