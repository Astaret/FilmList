package com.example.filmlist.presentation.storeMovies.events

sealed interface PurchaseEvent {
    data object showAllPurchases : PurchaseEvent
}