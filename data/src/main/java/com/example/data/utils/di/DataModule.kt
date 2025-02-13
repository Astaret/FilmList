package com.example.data.utils.di

import android.app.Application
import com.example.data.local.db.MovieDatabase
import com.example.data.local.db.MovieInfoDao
import com.example.data.repositories.MovieRepositoryImpl
import com.example.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public interface DataModule {
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    companion object{
        @Provides
        fun provideMovieDao(application: Application): MovieInfoDao {
            return MovieDatabase.getInstance(application).movieInfoDao()
        }
    }
}