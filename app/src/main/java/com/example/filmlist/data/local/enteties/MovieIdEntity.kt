package com.example.filmlist.data.local.enteties

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmlist.data.local.db.EntityState
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieIdEntity(
    @PrimaryKey
    val id: Int,
    val entityState: EntityState
): Parcelable

