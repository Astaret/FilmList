package com.example.domain.entities.dto_entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopMovieListDto (
    @SerializedName("results")
    @Expose
    val MovieList: List<MovieDto>,
    @SerializedName("page")
    @Expose
    val currentPage: String,
    @SerializedName("total_pages")
    @Expose
    val totalPages: String
)