package com.example.filmlist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieEntity(
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