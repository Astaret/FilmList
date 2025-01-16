package com.example.filmlist.data.web.api

import com.example.filmlist.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang:String = "ru-RU",
        @Query("page") page:Int ): TopMovieListDto
}