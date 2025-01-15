package com.example.filmlist.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmlist.data.local.enteties.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_entity ORDER BY id DESC")
    fun getMovieList(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    fun getMovieInfo(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList: List<MovieEntity>)

}