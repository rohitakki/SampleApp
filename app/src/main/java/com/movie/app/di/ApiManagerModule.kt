package com.movie.app.di

import com.movie.app.network.ApiInterface
import com.movie.app.network.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiManagerModule {

    @Singleton
    @Provides
    fun providesApiManager(apiInterface: ApiInterface): ApiManager {
        return ApiManager(apiInterface)
    }
}