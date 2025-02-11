package com.example.data.local.db

import androidx.room.TypeConverter
import com.example.domain.states.EntityState

class Converters {

    @TypeConverter
    fun toEntityState(value: Int)= enumValues<EntityState>()[value]

    @TypeConverter
    fun fromEntityState(value: EntityState) = value.ordinal
}