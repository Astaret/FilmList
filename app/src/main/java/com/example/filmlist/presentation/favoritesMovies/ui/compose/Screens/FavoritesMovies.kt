package com.example.filmlist.presentation.favoritesMovies.ui.compose.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.viewModels.FavoriteMoviesViewModel
import com.example.filmlist.presentation.ui_kit.components.MovieList

@Composable
fun favoriteMoviesScreen(
    vm: FavoriteMoviesViewModel = hiltViewModel(),
    navController: NavController,
    onNavigateToSearch: () -> Unit,
    onNavigateToBackMain: () -> Unit
) {

    LaunchedEffect(Unit) {
        vm.send(FavoriteEvent.showAllFavorites)

    }
    val favMovieState by vm.favState.collectAsState()
    val movieList = favMovieState.movieList

    if (!favMovieState.empty){
        favoriteListMovie(
            movieList = movieList,
            navController = navController,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToBackMain = onNavigateToBackMain)
    }else{
        EmptyScreen(onNavigateToBackMain)
    }

}

@Composable
fun favoriteListMovie(
    movieList: List<Movie>,
    navController: NavController,
    onNavigateToSearch: () -> Unit,
    onNavigateToBackMain: () -> Unit
) {
    val listState = rememberLazyListState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToBackMain() }) {
                Text(text = "◀")
            }
            Button(
                onClick = { onNavigateToSearch() }) {
                Text(text = "\uD83D\uDD0E")
            }
        }
        MovieList(
            movieList = movieList,
            listState = listState,
            navController = navController
        )
    }
}

@Composable
fun EmptyScreen(onNavigateToBackMain: () -> Unit) {
    Column {
        Button(
            onClick = { onNavigateToBackMain() }) {
            Text(text = "◀")
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ваш список избранного пуст",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}