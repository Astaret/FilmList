package com.example.filmlist.presentation.storeMovies.events

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface PurchaseEvent : BasedViewModel.Event {
    data object ShowAllPurchases : PurchaseEvent
    data object BuyMovie : PurchaseEvent
}