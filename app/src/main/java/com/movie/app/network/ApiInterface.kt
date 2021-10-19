package com.movie.app.network

import com.movie.app.model.DiscoverMoviesResponse
import com.movie.app.model.GenreListResponse
import com.movie.app.model.repositories.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiEndpoints.DISCOVER_MOVIES)
    suspend fun discoverMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): DiscoverMoviesResponse?

    @GET(ApiEndpoints.GET_GENRE_LIST)
    suspend fun getGenreList(@Query("api_key") apiKey: String): GenreListResponse?

    @GET(ApiEndpoints.GET_NOW_PLAYING)
    suspend fun getNowPlaying(@Query("api_key") apiKey: String): DiscoverMoviesResponse?

    @GET(ApiEndpoints.TRENDING_REPOSITORIES)
    suspend fun getTrendingRepositories(): List<Repository?>?
}