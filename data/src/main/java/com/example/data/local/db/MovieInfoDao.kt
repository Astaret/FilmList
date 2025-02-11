package com.example.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.enteties.db_enteties.MovieIdEntity
import com.example.domain.states.EntityState

@Dao
interface MovieInfoDao {

    @Query("SELECT * FROM movie_entity WHERE entityState = :state")
    suspend fun getMoviesByState(state: EntityState): List<MovieIdEntity>

    suspend fun getFavoriteMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityState.ISFAVORITE)
    }

    suspend fun getFromStoreMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityState.INSTORE)
    }

    suspend fun getFromBoughtMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityState.ISBOUGHT)
    }

    suspend fun getMovieListFromBd(state: com.example.domain.states.ListMovieState): List<MovieIdEntity>{
        return when(state){
            com.example.domain.states.ListMovieState.ISFAVORITE -> getFavoriteMovieList()
            com.example.domain.states.ListMovieState.INSTORE -> getFromStoreMovieList()
            com.example.domain.states.ListMovieState.ISBOUGHT -> getFromBoughtMovieList()
        }
    }

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id: Int):MovieIdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInMovieList(movieId: MovieIdEntity)

    @Query("""
    UPDATE movie_entity 
    SET entityState = :field
    WHERE id = :id """)
    suspend fun updateMovieField(id: Int, field: EntityState)

    @Delete
    suspend fun deleteMovie(movieId: MovieIdEntity)

}