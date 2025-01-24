package com.example.filmlist.presentation.libraryMovies.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.filmlist.presentation.core.Screen
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.viewModel.LibraryMoviesViewModel
import com.example.filmlist.presentation.ui_kit.components.MovieList

@Composable
fun LibraryScreen(
    vm: LibraryMoviesViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        vm.send(LibraryEvent.ShowAllBoughtMovies)

    }
    val librMovieState by vm.librState.collectAsState()
    val movieList = librMovieState.movieList

    if (!librMovieState.empty){
        libraryListMovie(
            movieList = movieList,
            navController = navController)
    }else{
        EmptyLibraryScreen({navController.navigate(Screen.MainScreen.route)})
    }

}


@Composable
private fun libraryListMovie(
    movieList: List<Movie>,
    navController: NavController
) {
    val listState = rememberLazyListState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {navController.navigate(Screen.MainScreen.route)}
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "BACK",
                    tint = Color.Black
                )
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
private fun EmptyLibraryScreen(onNavigateToBackMain: () -> Unit) {
    Column {
        IconButton(
            onClick = { onNavigateToBackMain() }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "BACK",
                tint = Color.Black
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ваша библиотека пуста",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}