package com.example.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.types.EntityType
import com.example.domain.types.ListMovieType

@Dao
interface MovieInfoDao {

    @Query("SELECT * FROM movie_entity WHERE entityType = :state")
    suspend fun getMoviesByState(state: EntityType): List<MovieIdEntity>

    suspend fun getFavoriteMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityType.ISFAVORITE)
    }

    suspend fun getFromStoreMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityType.INSTORE)
    }

    suspend fun getFromBoughtMovieList(): List<MovieIdEntity> {
        return getMoviesByState(EntityType.ISBOUGHT)
    }

    suspend fun getMovieListFromBd(type: ListMovieType): List<MovieIdEntity>{
        return when(type){
            ListMovieType.ISFAVORITE -> getFavoriteMovieList()
            ListMovieType.INSTORE -> getFromStoreMovieList()
            ListMovieType.ISBOUGHT -> getFromBoughtMovieList()
        }
    }

    @Query("SELECT * FROM movie_entity WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id: Int):MovieIdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInMovieList(movieId: MovieIdEntity)

    @Query(
        """
    UPDATE movie_entity 
    SET entityType = :field
    WHERE id = :id """
    )
    suspend fun updateMovieField(id: Int, field: EntityType)

    @Delete
    suspend fun deleteMovie(movieId: MovieIdEntity)

}