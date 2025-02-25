package com.example.filmlist.utils.di

import android.app.Application
import com.example.data.utils.di.DataModule
import com.example.filmlist.presentation.core.MovieActivity
import dagger.BindsInstance
import dagger.Component

@Component( modules =  [ViewModelModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(activity: MovieActivity)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance
            application: Application
        ):ApplicationComponent
    }
}