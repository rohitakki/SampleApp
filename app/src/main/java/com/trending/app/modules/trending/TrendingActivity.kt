package com.trending.app.modules.trending

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.trending.app.R
import com.trending.app.base.activity.BaseActivity
import com.trending.app.databinding.ActivityTrendingBinding
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

        binding.pullToRefresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun subscribeToLiveData() {
        trendingViewModel.trendingRepositoryLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                trendingRepositoryAdapter.addRepositories(it)
                showRecyclerView()
                binding.pullToRefresh.isRefreshing = false
            } else {
                showError()
                binding.pullToRefresh.isRefreshing = false
            }
        })
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
        binding.pullToRefresh.visibility = View.VISIBLE
        binding.errorLayoutView.visibility = View.GONE
    }

    private fun showLoading() {
        binding.shimmerLayout.startShimmerAnimation()
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.pullToRefresh.visibility = View.GONE
        binding.errorLayoutView.visibility = View.GONE
    }

    private fun showError() {
        binding.shimmerLayout.stopShimmerAnimation()
        binding.shimmerLayout.visibility = View.GONE
        binding.pullToRefresh.visibility = View.GONE
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
            trendingViewModel.getTrendingReposFromLocal()
        } else {
            showError()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun refreshData() {
        if (isConnectedToInternet()) {
            trendingViewModel.getTrendingRepositories()
        } else {
            showError()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trending
    }
}