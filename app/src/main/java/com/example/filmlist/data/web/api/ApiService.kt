package com.example.filmlist.data.web.api

import com.example.filmlist.data.web.dtos.TopMovieListDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authHeader: String,
        @Header("accept") acceptHeader: String = ACCEPT_HEADER_KEY
    ): TopMovieListDto
    //TODO и теперь тебе в каждый запрос придется вручную пихать этот хэдер accept. Найди способ сделать это один раз и для всего твоего ApiService

    companion object {
        private const val ACCEPT_HEADER_KEY = "application/json"
    }
}