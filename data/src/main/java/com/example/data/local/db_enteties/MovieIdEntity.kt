package com.example.data.local.db_enteties

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.states.EntityState
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieIdEntity(
    @PrimaryKey
    val id: Int,
    val entityState: EntityState
): Parcelable

