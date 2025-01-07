package com.example.filmlist.presentation.adapters

import android.app.Application
import com.example.filmlist.utils.di.DaggerApplicationComponent

class MovieApp:Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}