package com.example.filmlist.data.local.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toEntityState(value: Int)= enumValues<EntityState>()[value]

    @TypeConverter
    fun fromEntityState(value: EntityState) = value.ordinal
}