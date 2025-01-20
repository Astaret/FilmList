package com.example.filmlist.data.local.enteties

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val origLang: String,
    val overview: String,
    val poster: String,
    val title: String,
    val rating:String
): Parcelable