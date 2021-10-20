package com.movie.app.modules.trending

import android.os.Bundle
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
        setAdapter()
        subscribeToLiveData()
        trendingViewModel.getTrendingRepositories()
    }

    private fun subscribeToLiveData() {
        trendingViewModel.trendingRepositoryLiveData.observe(this, androidx.lifecycle.Observer {
            trendingRepositoryAdapter.addRepositories(it)
        })
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        (binding.trendingRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.trendingRecyclerView.layoutManager = layoutManager
        trendingRepositoryAdapter = TrendingRepositoryAdapter(this)
        binding.trendingRecyclerView.adapter = trendingRepositoryAdapter
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trending
    }
}