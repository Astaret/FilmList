package com.example.filmlist.presentation.topMovies.ui.Compose.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.filmlist.presentation.ui_kit.components.MovieList
import com.example.filmlist.presentation.topMovies.viewModels.MovieViewModel
import com.example.filmlist.presentation.ui_kit.events.loadingData
import com.example.filmlist.presentation.ui_kit.events.loadingNextPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navController: NavController,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    onNavigateToStore: () -> Unit
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

    LaunchedEffect(Unit) {
        viewModel.send(loadingData())
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onNavigateToFavorite() }
            ){
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite_screen",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { onNavigateToFavorite() }
            ){
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Library_screen",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { onNavigateToStore() }
            ){
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Store_screen",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { onNavigateToSearch() }
            ){
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search_screen",
                    tint = Color.Black
                )
            }
        }
        MovieList(movieList = movieList, listState = listState, navController = navController)
    }


}

