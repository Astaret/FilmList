package com.example.filmlist.utils.di

import android.app.Application
import com.example.filmlist.data.local.db.MovieDatabase
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.repositories.MovieRepositoryImpl
import com.example.filmlist.data.web.api.ApiFactory
import com.example.filmlist.data.web.api.ApiService
import com.example.filmlist.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl):MovieRepository

    companion object{
        @Provides
        fun provideMovieDao(application: Application): MovieInfoDao {
            return MovieDatabase.getInstance(application).movieInfoDao()
        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.api
        }
    }
}