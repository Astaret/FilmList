package com.example.filmlist.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmlist.data.local.enteties.MovieIdEntity
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.states.MovieState

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_entity WHERE isFavorite == 1")
    suspend fun getFavoriteMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE isInStore == 1")
    suspend fun getFromStoreMovieList(): List<MovieIdEntity>

    @Query("SELECT * FROM movie_entity WHERE isBought == 1")
    suspend fun getFromBoughtMovieList(): List<MovieIdEntity>

    suspend fun getMovieListFromBd(state: ListMovieState): List<MovieIdEntity>{
        return when(state){
            ListMovieState.ISFAVORITE -> getFavoriteMovieList()
            ListMovieState.INSTORE -> getFromStoreMovieList()
            ListMovieState.ISBOUGHT -> getFromBoughtMovieList()
        }
    }

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id: Int):MovieIdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInMovieList(movieId: MovieIdEntity)

    @Query("""
    UPDATE movie_entity 
    SET 
        isFavorite = CASE 
            WHEN :field = 'ISFAVORITE' THEN 1 
            ELSE 0
        END,
        
        isBought = CASE 
            WHEN :field = 'ISBOUGHT' THEN 1 
            ELSE 0 
        END,
        
        isInStore = CASE 
            WHEN :field = 'INSTORE' THEN 1 
            ELSE 0 
        END
    WHERE id = :id """)
    suspend fun updateMovieField(id: Int, field: String)

    @Delete
    suspend fun deleteMovie(movieId: MovieIdEntity)

}