package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.filmlist.domain.models.Movie

@Composable
fun MovieList(movieList: List<Movie>,
              navController: NavController,
              listState: LazyListState){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = listState
    ) {
        items(movieList.chunked(2)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                it.forEach{
                    MovieCard(movie = it, navController = navController)
                }
            }
        }
    }
}