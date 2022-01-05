package com.trending.app.modules.trending

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.trending.app.R
import com.trending.app.base.activity.BaseActivity
import com.trending.app.databinding.ActivityTrendingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class TrendingActivity(override val coroutineContext: CoroutineContext = Dispatchers.Main) : BaseActivity<ActivityTrendingBinding, TrendingViewModel>(), CoroutineScope {

    private val trendingViewModel: TrendingViewModel by viewModels()
    private lateinit var binding: ActivityTrendingBinding
    private lateinit var articleAdapter: ArticleAdapter

    //Debounce on Search field
    private val watcher = object : TextWatcher {
        private var searchFor = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == searchFor) {
                return
            }
            searchFor = searchText

            launch {
                delay(500)
                if (searchText != searchFor)
                    return@launch
                if (searchText.length >= 3) {
                    binding.errorLayout.searchField = searchText
                    getNewsArticles(searchText)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        binding = getViewDataBinding()!!
        binding.errorLayout.activity = this
        setAdapter()
        subscribeToLiveData()
        binding.pullToRefresh.setOnRefreshListener {
            refreshData()
        }
        binding.searchField.addTextChangedListener(watcher)
        getNewsArticles("")
    }

    /**
     * Observe changes in news articles list
     */
    private fun subscribeToLiveData() {
        trendingViewModel.newsArticlesLiveData.observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
                articleAdapter.submitList(it)
                hideLoading()
                binding.pullToRefresh.isRefreshing = false
            } else if (it == null) {
                showError()
                binding.pullToRefresh.isRefreshing = false
            } else if (it.isEmpty()) {
                hideLoading()
                showToastMessage("No results found for '${binding.searchField.text}'")
            }
        })

        trendingViewModel.totalResults.observe(this, Observer {
            binding.totalResults = it
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorLayoutView.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.pullToRefresh.visibility = View.GONE
        binding.errorLayoutView.visibility = View.VISIBLE
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        (binding.trendingRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.trendingRecyclerView.layoutManager = layoutManager
        articleAdapter = ArticleAdapter(this)
        binding.trendingRecyclerView.adapter = articleAdapter
    }

    /**
     * get top headlines or get news articles based on search
     */
    fun getNewsArticles(searchString: String) {
        showLoading()
        if (isConnectedToInternet()) {
            if (searchString.isEmpty()) {
                trendingViewModel.getTopHeadlines()
            } else {
                trendingViewModel.searchNews(searchString)
            }
        } else {
            binding.errorLayout.searchField = searchString
            showError()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    /**
     * On pull to refresh, retrieve top headlines
     */
    private fun refreshData() {
        binding.searchField.setText("")
        binding.errorLayout.searchField = ""
        trendingViewModel.getTopHeadlines()
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trending
    }
}