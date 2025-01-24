package com.example.filmlist.presentation.core

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object SearchScreen : Screen("search_screen")
    object DetailScreen : Screen("movieDetail_screen/{id}")
    object FavoriteScreen : Screen("favorite_screen")
    object StoreScreen : Screen("store_screen")
    object LibraryScreen : Screen("library_screen")
}