package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.Joke

interface MainView {
    suspend fun showJokes(jokes: List<Joke>)
    fun showError(errorMessage: String)
    fun addJoke()

}