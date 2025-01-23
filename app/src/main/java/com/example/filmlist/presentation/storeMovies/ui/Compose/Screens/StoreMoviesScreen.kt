package com.example.filmlist.presentation.storeMovies.ui.Compose.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.viewModels.StoreViewModel
import com.example.filmlist.presentation.ui_kit.components.MovieCard

@Composable
fun StoreScreen(
    onNavigateToBackMain: () -> Unit,
    viewModel: StoreViewModel = hiltViewModel(),
    navController: NavController,
){

    val storeState by viewModel.storeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.send(PurchaseEvent.showAllPurchases)
    }

    if (storeState.empty){
        EmptyStoreScreen(
            onNavigateToBackMain
        )
    }else{
        storeMovies(
            movieList = storeState.movieList,
            navController = navController,
            onNavigateToBackMain = onNavigateToBackMain
        )
    }


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
                        MovieCard(movie = it, navController = navController, moviePrice = (it.rating.toFloat() * 550.21).toFloat())
                    }
                }
            }
            item {
                Text(
                    text = String.format("%.2f", movieList.sumOf { it.rating.toFloat() * 550.21 })
                )
            }
        }
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