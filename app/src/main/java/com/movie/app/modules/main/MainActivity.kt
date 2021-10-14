package com.movie.app.modules.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.movie.app.R
import com.movie.app.base.activity.BaseActivity
import com.movie.app.databinding.ActivityMainBinding
import com.movie.app.model.MovieItem
import com.movie.app.modules.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var pagination: Boolean = false
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var discoverMovieAdapter: DiscoverMovieAdapter? = null

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = getViewDataBinding()!!
        binding.handler = this
        setDiscoverAdapter()
        subscribeToLiveData()
        isLoading = true
        pagination = false
        mainViewModel.discoverMovies(false)
        mainViewModel.getGenres()
    }

    private fun setDiscoverAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.movieRecycler.layoutManager = gridLayoutManager
        binding.movieRecycler.itemAnimator = DefaultItemAnimator()
        discoverMovieAdapter = DiscoverMovieAdapter(this)
        binding.movieRecycler.adapter = discoverMovieAdapter

        val endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener() {
                override fun onLoadMore() {
                    if (isLoading || isLastPage) {
                        return
                    }
                    isLoading = true
                    pagination = true
                    mainViewModel.discoverMovies(pagination)
                }

                override fun firstItemPosition(pos: Int) {

                }
            }
        binding.movieRecycler.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun subscribeToLiveData() {
        mainViewModel.movieItemsLiveData.observe(this, androidx.lifecycle.Observer {
            isLoading = false
            if (discoverMovieAdapter != null) {
                discoverMovieAdapter?.setMovies(pagination, it!!)
            }
        })

        mainViewModel.errorLiveData.observe(this, androidx.lifecycle.Observer {

        })
    }

    fun showMovieDetails(movieItem: MovieItem, view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            "poster_image"
        )
        startActivity(MovieDetailsActivity.newInstance(this, movieItem), options.toBundle())
    }
}