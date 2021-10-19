package com.movie.app.repository

import com.movie.app.model.DiscoverMoviesResponse
import com.movie.app.model.GenreListResponse
import com.movie.app.model.repositories.Repository
import com.movie.app.network.ApiManager
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiManager: ApiManager) {

    suspend fun discoverMovies(page: Int): DiscoverMoviesResponse? {
        return apiManager.discoverMovies(page)
    }

    suspend fun getNowPlaying(): DiscoverMoviesResponse? {
        return apiManager.getNowPlaying()
    }

    suspend fun getGenreList(): GenreListResponse? {
        return apiManager.getGenreList()
    }

    suspend fun getTrendingRepositories(): List<Repository?>? {
        return apiManager.getTrendingRepositories()
    }
}

