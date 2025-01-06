package com.example.filmlist.api

import com.example.filmlist.pojo.TopMovieList
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authHeader: String,
        @Header("accept") acceptHeader: String = ACCEPT_HEADER_KEY
    ):TopMovieList

    companion object {
        private const val ACCEPT_HEADER_KEY = "application/json"
    }
}