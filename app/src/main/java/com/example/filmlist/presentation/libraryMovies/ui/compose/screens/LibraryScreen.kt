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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.core.MainScreenRoute
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.states.LibraryState
import com.example.filmlist.presentation.libraryMovies.viewModel.LibraryMoviesViewModel
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.MovieList
import com.example.myapp.R

@Composable
fun LibraryScreen(
    vm: LibraryMoviesViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        vm.receiveEvent(LibraryEvent.ShowAllBoughtMovies)

    }

    val currentState by vm.state.collectAsStateWithLifecycle(initialValue = BasedViewModel.State.Loading)
    var librMovieState by remember { mutableStateOf<LibraryState?>(null) }

    LaunchedEffect(currentState) {
        (currentState as? LibraryState).let {
            librMovieState = it
        }
    }

    MainContainer(
        state = currentState
    ) {
        if (librMovieState?.empty != true) {
            librMovieState?.movieList?.let { movieList ->
                libraryListMovie(
                    movieList = movieList,
                    navController = navController
                )
            }
        } else {
            EmptyLibraryScreen({ navController.navigate(MainScreenRoute) })
        }
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
                onClick = { navController.navigate(MainScreenRoute) }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.Back),
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
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.Back),
                tint = Color.Black
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.your_library_empty),
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}