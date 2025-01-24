package com.example.filmlist.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmlist.data.local.enteties.MovieIdEntity

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_entity WHERE isFavorite == 1")
    suspend fun getFavoriteMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE isInStore == 1")
    suspend fun getFromStoreMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE isBought == 1")
    suspend fun getFromBoughtMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id: Int):MovieIdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInMovieList(movieId: MovieIdEntity)

    @Query("UPDATE movie_entity SET isFavorite = :newVal WHERE id = :id")
    suspend fun updateFavField(id: Int, newVal: Int)

    @Query("UPDATE movie_entity SET isInStore = :newVal WHERE id = :id")
    suspend fun updateStoreField(id: Int, newVal: Int)

    @Query("UPDATE movie_entity SET isBought = :newVal WHERE id = :id")
    suspend fun updateBoughtField(id: Int, newVal: Int)

    @Delete
    suspend fun deleteMovie(movieId: MovieIdEntity)

}