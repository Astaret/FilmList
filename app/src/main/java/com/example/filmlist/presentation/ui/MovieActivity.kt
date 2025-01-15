package com.example.filmlist.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.filmlist.presentation.adapters.MovieApp
import com.example.filmlist.presentation.viewModels.MovieScreen
import com.example.filmlist.presentation.viewModels.MovieViewModel
import com.example.filmlist.presentation.viewModels.MovieViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

        coroutineScope.launch {
            viewModel.loadData()
        }

        setContent{
            MovieScreen(viewModel)
        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}