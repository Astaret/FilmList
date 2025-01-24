package com.example.filmlist.data.local.enteties

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieIdEntity(
    @PrimaryKey
    val id: Int,
    var isFavorite: Int,
    var isInStore: Int,
    var isBought: Int
): Parcelable