package com.movie.app.network

import com.movie.app.model.DiscoverMoviesResponse
import com.movie.app.model.GenreListResponse
import com.movie.app.model.repositories.Repository
import javax.inject.Inject

open class ApiManager @Inject constructor(private val apiInterface: ApiInterface) {

    private val apiKey = "3834869ea04f707ee2d477ec1104be1a"

    suspend fun discoverMovies(page: Int): DiscoverMoviesResponse? {
        return apiInterface.discoverMovies(page, apiKey)
    }

    suspend fun getNowPlaying(): DiscoverMoviesResponse? {
        return apiInterface.getNowPlaying(apiKey)
    }

    suspend fun getGenreList(): GenreListResponse? {
        return apiInterface.getGenreList(apiKey)
    }

    suspend fun getTrendingRepositories(): List<Repository?>? {
        return apiInterface.getTrendingRepositories()
    }
}