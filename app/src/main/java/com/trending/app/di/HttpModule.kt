package com.trending.app.di

import com.trending.app.`interface`.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object HttpModule {

    @ApiModule
    @Singleton
    @Provides
    fun providesBaseUrl(): String {
        return Constants.baseUrl
    }

    @ApiModule
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.build()
        return okHttpClient.build()
    }

    @ApiModule
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @ApiModule
    @Singleton
    @Provides
    fun provideRetrofitClient(@ApiModule okHttpClient: OkHttpClient, @ApiModule baseUrl: String, @ApiModule converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}