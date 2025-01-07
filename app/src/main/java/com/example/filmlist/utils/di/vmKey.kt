package com.example.filmlist.utils.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class vmKey(val value: KClass<out ViewModel> )
