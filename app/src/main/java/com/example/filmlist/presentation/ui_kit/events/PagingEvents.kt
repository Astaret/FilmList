package com.example.filmlist.presentation.ui_kit.events

sealed interface PagingEvents{
    class loadingData: PagingEvents
    class loadingNextPage: PagingEvents
}

