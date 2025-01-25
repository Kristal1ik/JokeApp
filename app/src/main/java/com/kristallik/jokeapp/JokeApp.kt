package com.kristallik.jokeapp

import android.app.Application
import com.kristallik.jokeapp.di.module.AppComponent
import com.kristallik.jokeapp.di.module.DaggerAppComponent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JokeApp : Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
