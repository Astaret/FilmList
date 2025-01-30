package com.example.filmlist.data.local.db

enum class EntityState(val value: Int) {
    ISFAVORITE(1),
    ISBOUGHT(2),
    INSTORE(3),
    EMPTY(0);
}