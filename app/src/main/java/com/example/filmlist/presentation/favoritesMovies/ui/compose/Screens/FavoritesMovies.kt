package com.example.filmlist.presentation.favoritesMovies.ui.compose.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import com.example.filmlist.presentation.core.SearchScreenRoute
import com.example.filmlist.presentation.favoritesMovies.events.FavoriteEvent
import com.example.filmlist.presentation.favoritesMovies.states.FavoriteState
import com.example.filmlist.presentation.favoritesMovies.viewModels.FavoriteMoviesViewModel
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.MovieList
import com.example.myapp.R

@Composable
fun FavoriteMoviesScreen(
    vm: FavoriteMoviesViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        vm.receiveEvent(FavoriteEvent.ShowAllFavorites)

    }
    val currentState by vm.state.collectAsStateWithLifecycle(initialValue = BasedViewModel.State.Loading)

    var favMovieState by remember { mutableStateOf<FavoriteState?>(null) }

    LaunchedEffect(currentState) {
        (currentState as? FavoriteState).let {
            favMovieState = it
        }
    }

    MainContainer(
        state = currentState
    ) {
        if (favMovieState?.empty == true){
            EmptyFavoriteScreen { navController.navigate(MainScreenRoute) }
        }else{
            favMovieState?.movieList?.let {
                favoriteListMovie(
                    movieList = it,
                    navController = navController)
            }
        }
    }

}

@Composable
private fun favoriteListMovie(
    movieList: List<Movie>,
    navController: NavController,
) {
    val listState = rememberLazyListState()

    Box{
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick =  {navController.navigate(MainScreenRoute)}
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.Back),
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { navController.navigate(SearchScreenRoute)}
                ){
                    Icon(
                        imageVector = Icons.Default.Search,
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
}

@Composable
private fun EmptyFavoriteScreen(onNavigateToBackMain: () -> Unit) {
    Column {
        IconButton(
            onClick = { onNavigateToBackMain() }
        ){
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
                text = stringResource(R.string.your_fav_list_is_empty),
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}