package com.example.filmlist.presentation.core

import kotlinx.serialization.Serializable



@Serializable
object MainScreen
@Serializable
object SearchScreen
@Serializable
data class DetailScreen(val id: Int)
@Serializable
object FavoriteScreen
@Serializable
object StoreScreen
@Serializable
object LibraryScreen