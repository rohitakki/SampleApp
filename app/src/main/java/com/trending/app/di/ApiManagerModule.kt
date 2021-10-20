package com.trending.app.di

import com.trending.app.network.ApiInterface
import com.trending.app.network.ApiManager
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