package com.example.filmlist.presentation.ui.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filmlist.data.local.enteties.MovieEntity

@Composable
fun MovieList(movieList: List<MovieEntity>,
              listState: LazyListState){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieList.size) {
            MovieCard(
                movieList[it]
            )
        }
    }
}