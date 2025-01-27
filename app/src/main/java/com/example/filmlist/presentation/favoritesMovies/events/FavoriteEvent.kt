package com.example.filmlist.presentation.favoritesMovies.events

sealed interface FavoriteEvent{
    data object DeleteFromFavorite : FavoriteEvent
    data object ShowAllFavorites : FavoriteEvent
}