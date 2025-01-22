package com.example.filmlist.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmlist.data.local.enteties.MovieIdEntity

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_entity ORDER BY id DESC")
    suspend fun getMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id: Int):MovieIdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInMovieList(movieId: MovieIdEntity)

}