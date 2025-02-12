package com.example.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.entities.db_entities.MovieIdEntity

@Database(entities = [MovieIdEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieInfoDao(): MovieInfoDao

    companion object {
        private var db: MovieDatabase? = null
        private var LOCK = Any()
        private const val DB_NAME = "main_movie.db"

        fun getInstance(context: Context): MovieDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }
}