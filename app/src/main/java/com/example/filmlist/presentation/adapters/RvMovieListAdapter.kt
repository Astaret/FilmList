package com.example.filmlist.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.myapp.R
import com.example.myapp.databinding.MovieCardBinding
import com.squareup.picasso.Picasso

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
                val ratingTemplate = context.resources.getString(R.string.rating)

                tittleOfMovie.text = String.format(titleTemplate,title)
                overviewOfMovie.text = String.format(overViewTemplate,overview)
                langOfMovie.text = String.format(langTemplate,movie.origLang)
                when(movie.rating.toFloat()){
                    in 1.0..3.0 -> ratingOfMovie.setTextColor(context.getColor(R.color.red))
                    in 4.0..5.0 -> ratingOfMovie.setTextColor(context.getColor(R.color.orange))
                    in 6.0..7.0 -> ratingOfMovie.setTextColor(context.getColor(R.color.light_green))
                    in 8.0..10.0 -> ratingOfMovie.setTextColor(context.getColor(R.color.green))
                }
                ratingOfMovie.text = String.format(ratingTemplate,movie.rating)
                Picasso.get().load(this.poster).into(imageOfMovie)
            }
        }
    }

    inner class MovieListViewHolder(binding: MovieCardBinding):RecyclerView.ViewHolder(binding.root)
    {
        val imageOfMovie = binding.imgOfMovie
        val tittleOfMovie = binding.tVTittle
        val overviewOfMovie = binding.tVOverview
        val langOfMovie = binding.langOfMovie
        val ratingOfMovie = binding.ratingOfMovie
    }
}