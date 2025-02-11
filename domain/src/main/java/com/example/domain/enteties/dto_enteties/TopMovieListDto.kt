package com.example.domain.enteties.dto_enteties

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