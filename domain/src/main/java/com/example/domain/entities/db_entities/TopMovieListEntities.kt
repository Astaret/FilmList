package com.example.domain.entities.db_entities

import androidx.room.Entity

@Entity(tableName = "Movie_List")
data class TopMovieListEntities (
    val MovieList: List<MovieIdEntity>
)