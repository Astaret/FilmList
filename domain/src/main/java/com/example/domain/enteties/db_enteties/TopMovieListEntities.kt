package com.example.domain.enteties.db_enteties

import androidx.room.Entity

@Entity(tableName = "Movie_List")
data class TopMovieListEntities (
    val MovieList: List<MovieIdEntity>
)