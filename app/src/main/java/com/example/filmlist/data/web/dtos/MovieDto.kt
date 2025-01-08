package com.example.filmlist.data.web.dtos

import com.example.filmlist.data.web.api.ApiFactory.IMG_URL
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