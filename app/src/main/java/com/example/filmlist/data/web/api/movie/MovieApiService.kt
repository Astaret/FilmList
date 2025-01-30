package com.example.filmlist.data.web.api.movie

import com.example.filmlist.data.web.dtos.MovieDto
import com.example.filmlist.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int
    ): TopMovieListDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): TopMovieListDto

    @GET("movie/{id}")
    suspend fun getMovieInfo(
        @Path("id") movieId: Int,
        @Query("language") language: String = "ru-RU"
    ): MovieDto

}