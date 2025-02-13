package com.example.domain.entities.db_entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.types.EntityType
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_entity")
@Parcelize
data class MovieIdEntity(
    @PrimaryKey
    val id: Int,
    val entityType: EntityType
): Parcelable

