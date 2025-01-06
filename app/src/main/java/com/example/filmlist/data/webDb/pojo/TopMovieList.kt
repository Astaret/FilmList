package com.example.filmlist.data.webDb.pojo

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movie_List")
data class TopMovieList (
    @SerializedName("results")
    @Expose
    val MovieList: List<MovieEntity>
)