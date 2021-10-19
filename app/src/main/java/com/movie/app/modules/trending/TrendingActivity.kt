package com.movie.app.modules.trending

import android.os.Bundle
import androidx.activity.viewModels
import com.movie.app.R
import com.movie.app.base.activity.BaseActivity
import com.movie.app.databinding.ActivityTrendingBinding

class TrendingActivity : BaseActivity<ActivityTrendingBinding, TrendingViewModel>() {

    private val trendingViewModel: TrendingViewModel by viewModels()
    private lateinit var binding: ActivityTrendingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = getViewDataBinding()!!
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trending
    }
}