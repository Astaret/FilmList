package com.example.filmlist.presentation.core

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.filmlist.presentation.detailMovies.ui.compose.Screens.MovieDetailScreen
import com.example.filmlist.presentation.favoritesMovies.ui.compose.Screens.favoriteMoviesScreen
import com.example.filmlist.presentation.libraryMovies.ui.compose.screens.LibraryScreen
import com.example.filmlist.presentation.searchMovies.ui.compose.Screens.SearchScreen
import com.example.filmlist.presentation.storeMovies.ui.Compose.Screens.StoreScreen
import com.example.filmlist.presentation.topMovies.ui.Compose.Screens.MovieScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MainScreen) {

        composable<MainScreen> {
            MovieScreen(
                navController = navController
            )
        }

        composable<SearchScreen> {
            SearchScreen(navController = navController)
            BackHandler {
                navController.navigateUp()
            }
        }

        composable<DetailScreen> {
            val movieId: DetailScreen = it.toRoute()
            MovieDetailScreen(movieId = movieId.id.toString())
        }

        composable<FavoriteScreen> {
            favoriteMoviesScreen(navController = navController)
            BackHandler {
                navController.navigateUp()
            }
        }

        composable<StoreScreen> {
            StoreScreen(
                navController = navController
            )
            BackHandler {
                navController.navigateUp()
            }
        }

        composable<LibraryScreen> {
            LibraryScreen(
                navController = navController
            )
            BackHandler {
                navController.navigateUp()
            }
        }

    }
}