package com.example.filmlist.presentation.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmlist.R
import com.example.filmlist.data.mappers.RvMovieListAdapter
import com.example.filmlist.databinding.ActivityMovieBinding
import com.example.filmlist.presentation.viewModels.MovieViewModel

class MovieActivity : AppCompatActivity() {
    lateinit var viewModel: MovieViewModel
    private var binding: ActivityMovieBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = RvMovieListAdapter(this)
        binding?.rvMoviesList?.adapter = adapter

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movieList.observe(this, Observer {
            adapter.movieInfoList = it
        })
    }
}