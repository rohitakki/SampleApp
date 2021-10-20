package com.trending.app.network

import com.trending.app.model.repositories.Repository
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiEndpoints.TRENDING_REPOSITORIES)
    suspend fun getTrendingRepositories(): List<Repository?>?
}