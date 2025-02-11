package com.example.domain.enteties.dto_enteties

import com.google.gson.annotations.SerializedName


data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val origLang: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating:String
)