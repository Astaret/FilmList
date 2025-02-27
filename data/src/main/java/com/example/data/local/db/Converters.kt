package com.example.data.local.db

import androidx.room.TypeConverter
import com.example.domain.types.EntityType

class Converters {

    @TypeConverter
    fun toEntityState(value: Int)= enumValues<EntityType>()[value]

    @TypeConverter
    fun fromEntityState(value: EntityType) = value.ordinal
}