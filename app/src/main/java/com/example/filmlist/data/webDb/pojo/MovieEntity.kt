package com.example.filmlist.data.webDb.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(tableName = "movie_entity")
data class MovieEntity(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("original_language")
    @Expose
    val origLang: String,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("poster_path")
    @Expose
    val poster: String,
    @SerializedName("title")
    @Expose
    val title: String
)