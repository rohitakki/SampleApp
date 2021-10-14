package com.movie.app.modules.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.app.R
import com.movie.app.base.viewholder.BaseViewHolder
import com.movie.app.databinding.ItemMovieBinding
import com.movie.app.model.MovieItem
import com.movie.app.network.ApiEndpoints

class DiscoverMovieAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var movieList: ArrayList<MovieItem?>? = arrayListOf()
    lateinit var itemMovieBinding: ItemMovieBinding

    fun setMovies(pagination: Boolean, movies: List<MovieItem?>?) {
        if (pagination) {
            movieList?.addAll(movies!!)
        } else {
            movieList?.clear()
            movieList?.addAll(movies!!)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return DiscoverMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val discoverMovieViewHolder = holder as DiscoverMovieViewHolder
        val binding: ItemMovieBinding = discoverMovieViewHolder.itemMovieBinding
        val movieItem: MovieItem? = movieList?.get(position)
        binding.movieItem = movieItem
        this.itemMovieBinding = binding
        binding.activity = context as MainActivity
        binding.adapter = this@DiscoverMovieAdapter

            Glide.with(context)
            .load(ApiEndpoints.IMAGE_PATH + movieItem?.posterPath)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.movieThumbnail)

        binding.parentLayout.setOnClickListener {
            (context as MainActivity).showMovieDetails(movieItem!!, binding.movieThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size!!
    }

    class DiscoverMovieViewHolder(val itemMovieBinding: ItemMovieBinding) : BaseViewHolder(itemMovieBinding.root)
}