package com.example.filmlist.data.web.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMG_URL = "https://image.tmdb.org/t/p/w500/"
    //TODO URL обычно не хранятся здесь, они обычно в build.gradle уровня app, найди способ туда убрать все url

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: ApiService = retrofit.create(ApiService::class.java)

}