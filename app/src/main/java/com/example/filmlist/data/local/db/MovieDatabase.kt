package com.example.filmlist.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmlist.data.local.enteties.MovieIdEntity

@Database(entities = [MovieIdEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase:RoomDatabase() {
    abstract fun movieInfoDao(): MovieInfoDao

    companion object{
        private var db: MovieDatabase? = null
        private var LOCK = Any()
        private const val DB_NAME = "main_movie.db"

        fun getInstance(context:Context): MovieDatabase {
            synchronized(LOCK){
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
}