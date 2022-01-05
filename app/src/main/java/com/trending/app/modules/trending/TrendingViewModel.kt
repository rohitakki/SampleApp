package com.trending.app.modules.trending

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.trending.app.base.viewmodel.BaseViewModel
import com.trending.app.repository.DataRepository
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import com.trending.app.`interface`.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrendingViewModel @ViewModelInject constructor(
    @NonNull application: Application,
    private val repository: DataRepository,
    private val newsApiClient: NewsApiClient
) : BaseViewModel(application) {

    val newsArticlesLiveData = MutableLiveData<List<Article>?>()
    var totalResults = MutableLiveData<Int>()

    /**
     * get all the top headlines in India
     */
    fun getTopHeadlines() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            newsApiClient.getTopHeadlines(
                TopHeadlinesRequest.Builder()
                    .language("en")
                    .country("in")
                    .pageSize(100)
                    .build(),
                object : ArticlesResponseCallback {
                    override fun onSuccess(response: ArticleResponse) {
                        viewModelScope.launch(Dispatchers.Main) {
                            if (response.status == Constants.HTTP_OK) {
                                if (!response.articles.isNullOrEmpty()) {
                                    totalResults.value = response.totalResults
                                    newsArticlesLiveData.value = response.articles
                                } else {
                                    totalResults.value = 0
                                    newsArticlesLiveData.value = listOf()
                                }
                            }
                        }
                    }

                    override fun onFailure(throwable: Throwable) {
                        Log.e(TrendingViewModel::class.java.simpleName, throwable.message.toString())
                        viewModelScope.launch(Dispatchers.Main) {
                            totalResults.value = 0
                            newsArticlesLiveData.value = null
                        }
                    }
                }
            )
        }

        job.invokeOnCompletion {
            if (it != null) {
                Log.e(TrendingViewModel::class.java.simpleName, it.message.toString())
            }
        }
    }

    /**
     * Search news articles as per search text entered by user
     * @param searchString entered search text in search fields
     */
    fun searchNews(searchString: String) {
        val job = viewModelScope.launch(Dispatchers.Main) {
            newsApiClient.getEverything(
                EverythingRequest.Builder()
                    .q(searchString)
                    .pageSize(100)
                    .language("en")
                    .sortBy("publishedAt")
                    .build(),
                object : ArticlesResponseCallback {
                    override fun onSuccess(response: ArticleResponse) {
                        viewModelScope.launch(Dispatchers.Main) {
                            if (response.status == Constants.HTTP_OK) {
                                if (!response.articles.isNullOrEmpty()) {
                                    totalResults.value = response.totalResults
                                    newsArticlesLiveData.value = response.articles
                                } else {
                                    totalResults.value = 0
                                    newsArticlesLiveData.value = listOf()
                                }
                            }
                        }
                    }

                    override fun onFailure(throwable: Throwable) {
                        Log.e(TrendingViewModel::class.java.simpleName, throwable.message.toString())
                        viewModelScope.launch(Dispatchers.Main) {
                            totalResults.value = 0
                            newsArticlesLiveData.value = null
                        }
                    }
                }
            )
        }

        job.invokeOnCompletion {
            if (it != null) {
                Log.e(TrendingViewModel::class.java.simpleName, it.message.toString())
            }
        }
    }
}