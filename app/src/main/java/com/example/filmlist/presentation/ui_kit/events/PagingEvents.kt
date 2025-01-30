package com.example.filmlist.presentation.ui_kit.events

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

sealed interface PagingEvents: BasedViewModel.Event {
    class loadingData: PagingEvents
    class loadingNextPage: PagingEvents
    class loadingTotalPages: PagingEvents
}

