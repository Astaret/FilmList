package com.example.filmlist.domain.models

data class Movie (
    val id: Int,
    val origLang: String,
    val overview: String,
    val poster: String,
    val title: String,
    val rating:String
)