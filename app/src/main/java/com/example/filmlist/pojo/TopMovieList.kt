package com.example.filmlist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TopMovieList (
    @SerializedName("results")
    @Expose
    val MovieList: List<MovieEntity>
)