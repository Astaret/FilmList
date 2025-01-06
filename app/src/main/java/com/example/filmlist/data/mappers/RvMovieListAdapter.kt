package com.example.filmlist.data.mappers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmlist.R
import com.example.filmlist.data.webDb.pojo.MovieEntity
import com.example.filmlist.databinding.MovieCardBinding

class RvMovieListAdapter(private val context: Context):RecyclerView.Adapter<RvMovieListAdapter.MovieListViewHolder>() {
    var movieInfoList:List<MovieEntity> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = MovieCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieListViewHolder(binding)
    }

    override fun getItemCount() = movieInfoList.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movieInfoList[position]
        with(holder){
            with(movie){
                val titleTemplate = context.resources.getString(R.string.title)
                val overViewTemplate = context.resources.getString(R.string.overview)
                val langTemplate = context.resources.getString(R.string.lang)

                tittleOfMovie.text = String.format(titleTemplate,title)
                overviewOfMovie.text = String.format(overViewTemplate,overview)
                langOfMovie.text = String.format(langTemplate,movie.origLang)
            }
        }
    }

    inner class MovieListViewHolder(binding: MovieCardBinding):RecyclerView.ViewHolder(binding.root)
    {
        val tittleOfMovie = binding.tVTittle
        val overviewOfMovie = binding.tVOverview
        val langOfMovie = binding.langOfMovie
    }
}