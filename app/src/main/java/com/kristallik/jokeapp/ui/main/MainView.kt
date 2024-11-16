package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.Joke

interface MainView {
    fun showJokes(jokes: ArrayList<Joke>)
    fun showError(errorMessage: String)
    fun addJoke()
}