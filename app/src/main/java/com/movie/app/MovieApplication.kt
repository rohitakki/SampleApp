package com.movie.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {
    private var mInstance: MovieApplication? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}