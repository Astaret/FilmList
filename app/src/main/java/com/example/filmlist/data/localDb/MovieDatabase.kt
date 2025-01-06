package com.example.filmlist.data.localDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmlist.data.webDb.pojo.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase:RoomDatabase() {
    abstract fun movieInfoDao(): MovieInfoDao

    companion object{
        private var db: MovieDatabase? = null
        private var LOCK = Any()
        private const val DB_NAME = "main_movie.db"
        fun getInstance(context:Context):MovieDatabase{
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