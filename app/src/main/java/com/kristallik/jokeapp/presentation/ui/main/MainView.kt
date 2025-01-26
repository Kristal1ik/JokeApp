package com.kristallik.jokeapp.presentation.ui.main

import com.kristallik.jokeapp.data.Joke

interface MainView {
    suspend fun showJokes(jokes: List<Joke>)
    fun showError(errorMessage: String)
    fun addJoke()
    fun showToast(message: String)
}