package com.trending.app.modules.main

import android.app.Application
import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trending.app.base.viewmodel.BaseViewModel
import com.trending.app.model.MovieItem
import com.trending.app.repository.DataRepository
import com.trending.app.utils.GenreManager
import kotlinx.coroutines.*

class MainViewModel @ViewModelInject constructor(
    @NonNull application: Application,
    private val repository: DataRepository
) : BaseViewModel(application) {

    private var page: Int = 1
    val movieItemsLiveData = MutableLiveData<List<MovieItem?>?>()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    private var handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        commonError()
    }

    /**
     * get popular movies list with pagination
     */
    fun discoverMovies(pagination: Boolean) {
        if (pagination) {
            page++
        } else {
            page = 1
        }

        val job = viewModelScope.launch(Dispatchers.IO + handler) {
            val discoverMoviesResponse = repository.discoverMovies(page)
            if (discoverMoviesResponse != null) {
                if (discoverMoviesResponse.results != null && discoverMoviesResponse.results.isNotEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        movieItemsLiveData.value = discoverMoviesResponse.results
                    }
                }
            } else {
                commonError()
            }
        }

        job.invokeOnCompletion {
            if (it != null) {
                commonError()
            }
        }
    }

    fun getGenres() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            val genreListResponse = repository.getGenreList()
            if (genreListResponse != null && !genreListResponse.genres!!.isNullOrEmpty()) {
                //save genre list
                GenreManager.mInstance?.addGenres(genreListResponse)
            }
        }

        job.invokeOnCompletion {

        }
    }

    /**
     * Common error for all api calls
     */
    private fun commonError() {
        GlobalScope.launch(Dispatchers.Main) {
            errorLiveData.value = "Something went wrong"
        }
    }
}