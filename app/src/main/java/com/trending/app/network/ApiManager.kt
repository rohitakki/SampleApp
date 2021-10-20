package com.trending.app.network

import com.trending.app.model.repositories.Repository
import javax.inject.Inject

open class ApiManager @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getTrendingRepositories(): List<Repository?>? {
        return apiInterface.getTrendingRepositories()
    }
}