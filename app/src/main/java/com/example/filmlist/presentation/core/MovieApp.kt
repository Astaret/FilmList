package com.example.filmlist.presentation.core

import android.app.Application
import com.example.filmlist.utils.di.ApplicationComponent
import com.example.filmlist.utils.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp:Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)

    }
}