package com.example.filmlist.utils.di

import androidx.lifecycle.ViewModel
import com.example.filmlist.presentation.topMovies.viewModels.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface ViewModelModule {
    @Binds
    @IntoMap
    @vmKey(MovieViewModel::class)
    fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel
}