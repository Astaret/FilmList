package com.example.filmlist.presentation.favoritesMovies.events

sealed interface FavoriteEvent{
    data object deleteFromFavorite : FavoriteEvent
    data object showAllFavorites : FavoriteEvent
}