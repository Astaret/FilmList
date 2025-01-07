package com.example.filmlist.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmlist.R
import com.example.filmlist.databinding.ActivityMovieBinding
import com.example.filmlist.presentation.adapters.MovieApp
import com.example.filmlist.presentation.adapters.RvMovieListAdapter
import com.example.filmlist.presentation.viewModels.MovieViewModel
import com.example.filmlist.presentation.viewModels.MovieViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    lateinit var viewModel: MovieViewModel

    private val binding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as MovieApp).component
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

        coroutineScope.launch {
            viewModel.loadData()
        }

        val adapter = RvMovieListAdapter(this)
        binding.rvMoviesList.adapter = adapter
        viewModel.movieList.observe(this, Observer {
            adapter.movieInfoList = it
        })

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}