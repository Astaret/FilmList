package com.example.filmlist.presentation.ui.Compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "main_screen") {

        composable("main_screen") {
            MovieScreen(
                onNavigateToSearch = { navController.navigate("search_screen") }
            )
        }

        composable("search_screen") {
            SearchScreen()
            BackHandler {
                navController.navigateUp()
            }
        }
    }
}