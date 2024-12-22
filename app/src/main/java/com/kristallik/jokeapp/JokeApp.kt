package com.kristallik.jokeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JokeApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
