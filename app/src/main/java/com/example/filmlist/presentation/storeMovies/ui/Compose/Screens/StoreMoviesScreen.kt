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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.filmlist.presentation.core.MainScreenRoute
import com.example.filmlist.presentation.storeMovies.events.PurchaseEvent
import com.example.filmlist.presentation.storeMovies.states.StoreMovState
import com.example.filmlist.presentation.storeMovies.viewModels.StoreViewModel
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.movie_cards.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel(),
    navController: NavController,
) {

    val currentState by viewModel.state.collectAsStateWithLifecycle(initialValue = BasedViewModel.State.Loading)
    val listState = rememberLazyListState()

    var storeState by remember { mutableStateOf<StoreMovState?>(null) }
    var showModalSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.receiveEvent(PurchaseEvent.ShowAllPurchases)
    }

    LaunchedEffect(currentState) {
        (currentState as? StoreMovState).let {
            storeState = it
        }
    }

    MainContainer(
        state = currentState
    ) {
        if (storeState?.empty == true) {
            EmptyStoreScreen(
                { navController.navigate(MainScreenRoute) }
            )
        } else {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = { navController.navigate(MainScreenRoute) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "BACK",
                            tint = Color.Black
                        )
                    }

                    Row {
                        Text(
                            text = "Сумма к оплате:"
                        )
                        Text(
                            text = String.format("%.2f", storeState?.totalPrice)
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
                    storeState?.let {storeState->
                        items(storeState.movieList.chunked(2)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                it.forEach {
                                    MovieCard(
                                        movie = it,
                                        navController = navController,
                                        moviePrice = it.price,
                                    )
                                }
                            }
                        }
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
                                    text = "${String.format("%.2f", storeState?.totalPrice)}",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                Button(
                                    onClick = {
                                        viewModel.receiveEvent(PurchaseEvent.BuyMovie)
                                        showModalSheet = false
                                    },
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
    }




}


@Composable
private fun EmptyStoreScreen(onNavigateToBackMain: () -> Unit) {
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
                text = "Ваша корзина пуста",
                fontSize = 24.sp,
                color = Color.Gray
            )
        }
    }

}