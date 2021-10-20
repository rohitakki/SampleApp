package com.trending.app.di

import com.trending.app.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideUserService(@ApiModule retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}