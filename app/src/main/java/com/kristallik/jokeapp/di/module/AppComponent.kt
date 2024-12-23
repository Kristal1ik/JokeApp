package com.kristallik.jokeapp.di.module

import android.content.Context
import com.kristallik.jokeapp.presentation.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component

interface AppComponent {
    fun inject(activity: MainActivity)
    @Component.Factory
    interface AppComponentFactory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}