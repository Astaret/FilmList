package com.example.filmlist.presentation.core

import kotlinx.serialization.Serializable


@Serializable
object MainScreenRoute

@Serializable
object SearchScreenRoute

@Serializable
data class DetailScreenRoute(val id: Int)

@Serializable
object FavoriteScreenRoute

@Serializable
object StoreScreenRoute

@Serializable
object LibraryScreenRoute

@Serializable
object CameraScreenRoute