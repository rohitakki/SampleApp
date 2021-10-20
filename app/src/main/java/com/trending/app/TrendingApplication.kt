package com.trending.app

import android.app.Application
import com.trending.app.preferences.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrendingApplication : Application() {
    private var mInstance: TrendingApplication? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppPreferences.init(this)
    }
}