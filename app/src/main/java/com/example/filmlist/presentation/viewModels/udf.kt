package com.example.filmlist.presentation.viewModels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmlist.presentation.ui.Compose.MovieCard

@Composable
fun MovieScreen(viewModel: MovieViewModel = viewModel()) {
    val movieList = viewModel.movieList.collectAsState(emptyList()).value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movieList.size) {
            Card(
                tittle = movieList[it].title,
                lang = movieList[it].origLang,
                rating = movieList[it].rating,
                overview = movieList[it].overview,
                res = movieList[it].poster
            )
        }
    }
}

@Composable
fun Card(tittle: String, lang: String, rating: String, overview: String, res: String) {
    MovieCard(
        title = tittle,
        language = "язык: $lang",
        rating = "рейтинг: $rating",
        overview = "$overview",
        imageRes = res
    )
}
