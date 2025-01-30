package com.example.filmlist.utils.di

import android.app.Application
import com.example.filmlist.data.local.db.MovieDatabase
import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.repositories.MovieRepositoryImpl
import com.example.filmlist.data.web.api.movie.MovieApiFactory
import com.example.filmlist.data.web.api.movie.MovieApiService
import com.example.filmlist.data.web.api.qr_code.QrCodeApiFactory
import com.example.filmlist.data.web.api.qr_code.QrCodeApiService
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
        fun provideApiService(): MovieApiService {
            return MovieApiFactory.api
        }

        @Provides
        fun provideQrCodeApiService(): QrCodeApiService {
            return QrCodeApiFactory.api
        }

    }
}