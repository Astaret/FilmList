package com.example.filmlist.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.filmlist.presentation.adapters.MovieApp
import com.example.filmlist.presentation.ui.Compose.MovieScreen
import com.example.filmlist.presentation.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

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


        setContent{
            MovieScreen(viewModel)
        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}