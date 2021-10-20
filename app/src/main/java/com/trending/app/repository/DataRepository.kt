package com.trending.app.repository

import com.trending.app.model.repositories.Repository
import com.trending.app.network.ApiManager
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiManager: ApiManager) {

    suspend fun getTrendingRepositories(): List<Repository?>? {
        return apiManager.getTrendingRepositories()
    }
}

