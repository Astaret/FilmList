package com.example.filmlist.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import com.example.filmlist.presentation.adapters.MovieApp
import com.example.filmlist.presentation.ui.Compose.MovieList
import com.example.filmlist.presentation.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()


    private val component by lazy {
        (application as MovieApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        setContent{
            Text("hello")
            MovieList(viewModel = viewModel, context = this)
        }

    }
}