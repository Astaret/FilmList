package com.example.filmlist.data.web.dtos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopMovieListDto (
    @SerializedName("results")
    @Expose
    val MovieList: List<MovieDto>,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("total_pages")
    @Expose
    val totalPages:Int,
    @SerializedName("total_results")
    @Expose
    val totalResults:Int
)
