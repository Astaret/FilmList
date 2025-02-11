package com.example.data.web.api

import com.example.filmlist.data.web.dtos.MovieDto
import com.example.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int
    ): com.example.data.web.dtos.TopMovieListDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): com.example.data.web.dtos.TopMovieListDto

    @GET("movie/{id}")
    suspend fun getMovieInfo(
        @Path("id") movieId: Int,
        @Query("language") language: String = "ru-RU"
    ): MovieDto

}