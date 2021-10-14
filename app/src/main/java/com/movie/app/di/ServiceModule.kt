package com.movie.app.di

import com.movie.app.network.ApiInterface
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