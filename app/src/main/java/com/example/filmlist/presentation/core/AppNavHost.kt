package com.example.filmlist.presentation.core

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmlist.presentation.topMovies.ui.Compose.Screens.MovieScreen
import com.example.filmlist.presentation.searchMovies.ui.compose.Screens.SearchScreen
import com.example.filmlist.presentation.detailMovies.ui.compose.Screens.movieDetailScreen
import com.example.filmlist.presentation.favoritesMovies.ui.compose.Screens.favoriteMoviesScreen
import com.example.filmlist.presentation.storeMovies.ui.Compose.Screens.StoreScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "main_screen") {

        composable("main_screen") {
            MovieScreen(
                navController = navController,
                onNavigateToSearch = { navController.navigate("search_screen")},
                onNavigateToFavorite = {navController.navigate("favorite_movie_screen")},
                onNavigateToStore = {navController.navigate("store_screen")}
            )
        }

        composable("search_screen") {
            SearchScreen(navController = navController)
            BackHandler {
                navController.navigateUp()
            }
        }

        composable("movieDetail_screen/{id}") {
            val movieId = it.arguments?.getString("id")
            movieId?.let {
                movieDetailScreen(movieId = it)
            }
        }

        composable("favorite_movie_screen") {
            favoriteMoviesScreen(navController = navController,
                onNavigateToSearch = { navController.navigate("search_screen")},
                onNavigateToBackMain = {navController.navigate("main_screen")}
            )
            BackHandler {
                navController.navigateUp()
            }
        }

        composable("store_screen") {
            StoreScreen(
                onNavigateToBackMain = {navController.navigate("main_screen")},
                navController = navController
            )
            BackHandler {
                navController.navigateUp()
            }
        }

    }
}