package com.example.filmlist.presentation.core

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        startDestination = Screen.MainScreen.route) {

        composable(route = Screen.MainScreen.route) {
            MovieScreen(
                navController = navController
            )
        }

        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
            BackHandler {
                navController.navigateUp()
            }
        }

        composable(route = Screen.DetailScreen.route) {
            val movieId = it.arguments?.getString("id")
            movieId?.let {
                MovieDetailScreen(movieId = it)
            }
        }

        composable(route = Screen.FavoriteScreen.route) {
            favoriteMoviesScreen(navController = navController)
            BackHandler {
                navController.navigateUp()
            }
        }

        composable(route = Screen.StoreScreen.route) {
            StoreScreen(
                navController = navController
            )
            BackHandler {
                navController.navigateUp()
            }
        }

        composable(route = Screen.LibraryScreen.route) {
            LibraryScreen(
                navController = navController
            )
            BackHandler {
                navController.navigateUp()
            }
        }

    }
}