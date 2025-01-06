package com.example.filmlist.data.localDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmlist.data.webDb.pojo.MovieEntity

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_entity ORDER BY id DESC")
    fun getMovieList(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    fun getMovieInfo(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieList: List<MovieEntity>)

}