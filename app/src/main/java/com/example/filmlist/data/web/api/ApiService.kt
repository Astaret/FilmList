package com.example.filmlist.data.web.api

import com.example.filmlist.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated?language=ru-RU&page1")
    suspend fun getTopRatedMovies(): TopMovieListDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): TopMovieListDto

}