package com.example.filmlist.presentation.topMovies.ui.Compose.Screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.filmlist.presentation.core.CameraScreenRoute
import com.example.filmlist.presentation.core.FavoriteScreenRoute
import com.example.filmlist.presentation.core.LibraryScreenRoute
import com.example.filmlist.presentation.core.SearchScreenRoute
import com.example.filmlist.presentation.core.StoreScreenRoute
import com.example.filmlist.presentation.topMovies.viewModels.MovieViewModel
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.MovieList
import com.example.filmlist.presentation.ui_kit.components.PermissionDialog
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest
import com.example.filmlist.presentation.ui_kit.events.PagingEvents

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navController: NavController
) {
    val topMovieState by viewModel.state.collectAsState()

    val movieList = topMovieState.movieList
    val listState = rememberLazyListState()
    var showScanner by remember { mutableStateOf(false) }
    val isAtEnd = listState.layoutInfo.visibleItemsInfo
        .lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 3


    if (showScanner) {
        LaunchedEffect(Unit) {
            navController.navigate(CameraScreenRoute)
        }
    }

    LaunchedEffect(isAtEnd) {
        if (isAtEnd) {
            viewModel.receiveEvent(PagingEvents.loadingNextPage())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.receiveEvent(PagingEvents.loadingData())
        viewModel.receiveEvent(PagingEvents.loadingTotalPages())
    }

    val permissions = remember {
        mutableStateOf(PermissionRequest())
    }

    MainContainer(
        permissionRequest = permissions.value,
        isLoading = topMovieState.isLoading
        ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.navigate(FavoriteScreenRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite_screen",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = { navController.navigate(LibraryScreenRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Library_screen",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = { navController.navigate(StoreScreenRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Store_screen",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = { navController.navigate(SearchScreenRoute) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search_screen",
                        tint = Color.Black
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                MovieList(
                    movieList = movieList,
                    listState = listState,
                    navController = navController
                )
                IconButton(
                    onClick = {
                        permissions.value = PermissionRequest(
                            permissions = listOf(
                                Manifest.permission.CAMERA
                            ),
                            permissionDialog = { PermissionDialog() },
                            onGranted = {
                                showScanner = true
                            }
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    colors = IconButtonColors(
                        containerColor = Color.Green,
                        contentColor = Color.Black,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Search_screen",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

