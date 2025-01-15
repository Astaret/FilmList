package com.example.filmlist.data.web.api

import com.example.filmlist.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("movie/top_rated?language=ru-RU&page1")
    suspend fun getTopRatedMovies(): TopMovieListDto

}