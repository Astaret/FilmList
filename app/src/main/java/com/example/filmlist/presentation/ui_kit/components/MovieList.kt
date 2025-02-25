package com.example.filmlist.presentation.ui_kit.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.ui_kit.components.movie_cards.MovieCard

@Composable
fun MovieList(
    movieList: List<Movie>,
    navController: NavController,
    listState: LazyListState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
        ,
        state = listState
    ) {
        items(movieList) {
            MovieCard(movie = it, navController = navController)
        }
    }
}