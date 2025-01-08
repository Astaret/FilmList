package com.example.filmlist.presentation.adapters

import android.app.Application
import com.example.filmlist.utils.di.ApplicationComponent
import com.example.filmlist.utils.di.DaggerApplicationComponent

class MovieApp:Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)

    }
}