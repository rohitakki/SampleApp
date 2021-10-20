package com.movie.app.modules.trending

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.movie.app.R
import com.movie.app.base.activity.BaseActivity
import com.movie.app.databinding.ActivityTrendingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingActivity : BaseActivity<ActivityTrendingBinding, TrendingViewModel>() {

    private val trendingViewModel: TrendingViewModel by viewModels()
    private lateinit var binding: ActivityTrendingBinding
    private lateinit var trendingRepositoryAdapter: TrendingRepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = getViewDataBinding()!!
        binding.errorLayout.activity = this
        setAdapter()
        subscribeToLiveData()
        getTrendingRepositories()
    }

    private fun subscribeToLiveData() {
        trendingViewModel.trendingRepositoryLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                trendingRepositoryAdapter.addRepositories(it)
                showRecyclerView()
            } else {
                showError()
            }
        })
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
        binding.trendingRecyclerView.visibility = View.VISIBLE
        binding.errorLayoutView.visibility = View.GONE
    }

    private fun showLoading() {
        binding.shimmerLayout.startShimmerAnimation()
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.trendingRecyclerView.visibility = View.GONE
        binding.errorLayoutView.visibility = View.GONE
    }

    private fun showError() {
        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
        binding.trendingRecyclerView.visibility = View.GONE
        binding.errorLayoutView.visibility = View.VISIBLE
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        (binding.trendingRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.trendingRecyclerView.layoutManager = layoutManager
        trendingRepositoryAdapter = TrendingRepositoryAdapter(this)
        binding.trendingRecyclerView.adapter = trendingRepositoryAdapter
    }

    fun getTrendingRepositories() {
        if (isConnectedToInternet()) {
            showLoading()
            trendingViewModel.getTrendingRepositories()
        } else {
            showError()
        }
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trending
    }
}