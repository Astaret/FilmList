package com.example.filmlist.presentation.storeMovies.events

sealed interface PurchaseEvent {
    data object ShowAllPurchases : PurchaseEvent
    data object BuyMovie : PurchaseEvent
}