package com.example.filmlist.presentation.ui.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filmlist.data.local.enteties.MovieEntity

@Composable
fun MovieList(movieList: List<MovieEntity>){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieList.size) {
            MovieCard(
                movieList[it].title,
                movieList[it].origLang,
                movieList[it].rating,
                movieList[it].overview,
                movieList[it].poster
            )
        }
    }
}