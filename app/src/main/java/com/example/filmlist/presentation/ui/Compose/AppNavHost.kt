package com.example.filmlist.presentation.ui.Compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmlist.presentation.viewModels.MovieViewModel
import com.example.filmlist.presentation.viewModels.SearchMovieViewModel

@Composable
fun AppNavHost(
    viewModel: MovieViewModel = viewModel(),
    viewModelSearch: SearchMovieViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val movieList = viewModel.movieList.collectAsState(emptyList()).value
    NavHost(navController = navController, startDestination = "main_screen") {

        composable("main_screen") {
            MovieScreen(
                movieList = movieList,
                onNavigateToSearch = {
                navController.navigate("search_screen")
            })
        }

        composable("search_screen") {
            SearchScreen(
                viewModel = viewModelSearch
            )
        }
    }
}