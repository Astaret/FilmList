package com.example.filmlist.presentation.ui.Compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.presentation.viewModels.MovieViewModel
import com.example.filmlist.presentation.viewModels.loadingNextPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit
) {
    val topMovieState by viewModel.movieState.collectAsState()

    val movieList = topMovieState.movieList
    val listState = rememberLazyListState()
    val isAtEnd = listState.layoutInfo.visibleItemsInfo
        .lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 3

    LaunchedEffect(isAtEnd) {
        if (isAtEnd) {
            viewModel.send(loadingNextPage())
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToSearch() }) {
                Text(text = "⭐")
            }

            Button(
                onClick = { onNavigateToSearch() }) {
                Text(text = "\uD83D\uDD0E")
            }
        }
        MovieList(movieList = movieList,
            listState = listState)
    }


}

