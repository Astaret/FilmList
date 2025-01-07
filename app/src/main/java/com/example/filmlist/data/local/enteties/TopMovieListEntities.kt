package com.example.filmlist.data.local.enteties

import androidx.room.Entity

@Entity(tableName = "Movie_List")
data class TopMovieListEntities (
    val MovieList: List<MovieEntity>
)