package com.example.domain.entities

data class Movie (
    val id: Int,
    val origLang: String,
    val overview: String,
    val poster: String,
    val title: String,
    val rating:String,
    val price:Float = 0f
)