package com.example.filmlist.presentation.storeMovies.ui.Compose.Screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.ui_kit.components.MovieList

@Composable
fun StoreScreen(
    onNavigateToBackMain: () -> Unit
){
    EmptyStoreScreen(onNavigateToBackMain)
}

@Composable
fun storeMovies(
    movieList: List<Movie>,
    navController: NavController,
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
                onClick = {  }) {
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
fun EmptyStoreScreen(onNavigateToBackMain: () -> Unit) {
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
                text = "Ваш список покупок пуст",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}