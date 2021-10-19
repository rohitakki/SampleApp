package com.movie.app.modules.trending

import android.app.Application
import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movie.app.base.viewmodel.BaseViewModel
import com.movie.app.model.repositories.Repository
import com.movie.app.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrendingViewModel @ViewModelInject constructor(
    @NonNull application: Application,
    private val repository: DataRepository
) : BaseViewModel(application) {

    val trendingRepositoryLiveData = MutableLiveData<List<Repository?>?>()

    fun getTrendingRepositories() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            val repositories = repository.getTrendingRepositories()
            if (!repositories.isNullOrEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    trendingRepositoryLiveData.value = repositories
                }
            }
        }

        job.invokeOnCompletion {

        }
    }
}