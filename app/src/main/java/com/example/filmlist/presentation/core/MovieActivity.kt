package com.example.filmlist.presentation.core

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.filmlist.presentation.topMovies.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

@AndroidEntryPoint
class MovieActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()

    private val component by lazy {
        (application as MovieApp).component
    }


    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        setContent {
            AppNavHost()

        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
