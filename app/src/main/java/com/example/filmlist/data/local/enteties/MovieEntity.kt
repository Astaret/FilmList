package com.example.filmlist.data.local.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "movie_entity")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val origLang: String,
    val overview: String,
    val poster: String,
    val title: String,
    val rating:String
)