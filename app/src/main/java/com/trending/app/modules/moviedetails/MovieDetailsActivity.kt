package com.trending.app.modules.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.trending.app.R
import com.trending.app.base.activity.BaseActivity
import com.trending.app.databinding.ActivityMovieDetailsBinding
import com.trending.app.model.MovieItem
import com.trending.app.modules.main.MainViewModel
import com.trending.app.network.ApiEndpoints
import com.trending.app.utils.GenreManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding, MainViewModel>() {


    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieItem: MovieItem

    companion object {
        const val MOVIE_ITEM: String = "movie_item"

        fun newInstance(context: Context, movieItem: MovieItem): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ITEM, movieItem)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun getBindingVariable(): Int {
        return 0;
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_movie_details
    }

    private fun init() {
        binding = getViewDataBinding()!!
        binding.handler = this
        binding.genreManager = GenreManager.mInstance
        getIntentData()
    }

    private fun getIntentData() {
        if (intent != null && intent?.extras != null) {
            movieItem = intent?.extras?.getSerializable(MOVIE_ITEM) as MovieItem
            setMovieData(movieItem)
        }
    }

    private fun setMovieData(movieItem: MovieItem) {
        binding.movieItem = movieItem
        Glide.with(this)
            .load(ApiEndpoints.IMAGE_PATH + movieItem.posterPath)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.posterImage)

        Glide.with(this)
            .load(ApiEndpoints.IMAGE_PATH + movieItem.backdropPath)
            .centerCrop()
            .into(binding.backgroundImage)
    }
}