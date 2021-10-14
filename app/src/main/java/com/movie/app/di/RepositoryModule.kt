package com.movie.app.di

import com.movie.app.network.ApiManager
import com.movie.app.repository.DataRepository
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