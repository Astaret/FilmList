package com.example.filmlist.presentation.favoritesMovies.events

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface FavoriteEvent : BasedViewModel.Event {
    data object DeleteFromFavorite : FavoriteEvent
    data object ShowAllFavorites : FavoriteEvent
}