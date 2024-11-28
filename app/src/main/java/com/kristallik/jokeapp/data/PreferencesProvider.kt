package com.kristallik.jokeapp.data


interface PreferencesProvider {
    fun getJokesCount(): Int
    fun setJokesCount(count: Int)
}