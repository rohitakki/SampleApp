package com.trending.app.di

import com.trending.app.network.ApiManager
import com.trending.app.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepository(apiManager: ApiManager): DataRepository {
        return DataRepository(apiManager)
    }
}