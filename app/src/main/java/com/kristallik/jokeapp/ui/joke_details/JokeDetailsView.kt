package com.kristallik.jokeapp.ui.joke_details

import com.kristallik.jokeapp.data.Joke

interface JokeDetailsView {
    fun showJokeInfo(joke: Joke)
    fun showErrorAndCloseScreen(errorMessage: String)
}