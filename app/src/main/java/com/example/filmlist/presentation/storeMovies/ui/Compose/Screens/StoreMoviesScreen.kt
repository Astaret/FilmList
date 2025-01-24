package com.example.filmlist.presentation.storeMovies.ui.Compose.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.storeMovies.viewModels.StoreViewModel
import com.example.filmlist.presentation.ui_kit.components.MovieCard

@Composable
fun StoreScreen(
    onNavigateToBackMain: () -> Unit,
    viewModel: StoreViewModel = hiltViewModel(),
    navController: NavController,
) {

    val storeState by viewModel.storeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.send(PurchaseEvent.ShowAllPurchases)
    }

    if (storeState.empty) {
        EmptyStoreScreen(
            onNavigateToBackMain
        )
    } else {
        StoreMovies(
            movieList = storeState.movieList,
            navController = navController,
            storeState = storeState,
            onNavigateToBackMain = onNavigateToBackMain
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreMovies(
    movieList: List<Movie>,
    navController: NavController,
    storeState: StoreMovState,
    onNavigateToBackMain: () -> Unit
) {
    val listState = rememberLazyListState()
    var showModalSheet by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = { onNavigateToBackMain() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "BACK",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = { showModalSheet = true }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "BYU?",
                    tint = Color.Black
                )
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
                    it.forEach {
                        MovieCard(
                            movie = it,
                            navController = navController,
                            moviePrice = it.price ?: 0f,
                        )
                    }
                }
            }
            item {
                Text(
                    text = String.format("%.2f", storeState.totalPrice)
                )
            }
        }

        if (showModalSheet) {
            ModalBottomSheet(
                onDismissRequest = { showModalSheet = false },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Итого к оплате",
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "${String.format("%.2f", storeState.totalPrice)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = { showModalSheet = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Оплатить",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun EmptyStoreScreen(onNavigateToBackMain: () -> Unit) {
    Column {
        IconButton(
            onClick = { onNavigateToBackMain() }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
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
                text = "Ваш список покупок пуст",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}