package com.movie.app.modules.trending

import android.app.Application
import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movie.app.base.viewmodel.BaseViewModel
import com.movie.app.model.repositories.Repository
import com.movie.app.preferences.AppPreferences
import com.movie.app.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrendingViewModel @ViewModelInject constructor(
    @NonNull application: Application,
    private val repository: DataRepository
) : BaseViewModel(application) {

    val trendingRepositoryLiveData = MutableLiveData<List<Repository?>?>()

    /**
     * Get trending repositories from API
     */
    fun getTrendingRepositories() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            val repositories = repository.getTrendingRepositories()
            viewModelScope.launch(Dispatchers.Main) {
                if (!repositories.isNullOrEmpty()) {
                    trendingRepositoryLiveData.value = repositories
                    saveToLocal(repositories)
                } else {
                    trendingRepositoryLiveData.value = null
                }
            }
        }

        job.invokeOnCompletion {
            if (it != null) {
                viewModelScope.launch(Dispatchers.Main) {
                    trendingRepositoryLiveData.value = null
                }
            }
        }
    }

    /**
     * Save trending repositories to shared preferences
     */
    private fun saveToLocal(repositories: List<Repository?>) {
        GlobalScope.launch(Dispatchers.IO) {
            AppPreferences.setTrendingRepos(repositories)
        }
    }

    /**
     * Read trending repositories from shared preferences
     */
    fun getTrendingReposFromLocal() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            val repositories = AppPreferences.getTrendingRepos()
            viewModelScope.launch(Dispatchers.Main) {
                if (!repositories.isNullOrEmpty()) {
                    trendingRepositoryLiveData.value = repositories
                } else {
                    getTrendingRepositories()
                }
            }
        }

        job.invokeOnCompletion {
            if (it != null) {
                viewModelScope.launch(Dispatchers.Main) {
                    getTrendingRepositories()
                }
            }
        }
    }
}