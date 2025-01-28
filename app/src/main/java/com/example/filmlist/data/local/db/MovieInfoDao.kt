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
    SET entityState = :field
    WHERE id = :id """)
    suspend fun updateMovieField(id: Int, field: EntityState)

    @Delete
    suspend fun deleteMovie(movieId: MovieIdEntity)

}