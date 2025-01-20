package com.example.filmlist.presentation.ui.Compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmlist.presentation.ui.Compose.Screens.MovieScreen
import com.example.filmlist.presentation.ui.Compose.Screens.SearchScreen
import com.example.filmlist.presentation.ui.Compose.Screens.movieDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "main_screen") {

        composable("main_screen") {
            MovieScreen(
                navController = navController,
                onNavigateToSearch = { navController.navigate("search_screen")}
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

    }
}