package com.kristallik.jokeapp.ui.joke_details

import com.kristallik.jokeapp.data.Joke

interface JokeDetailsView {
    companion object {
        const val VALUE_IF_ERROR = -1
    }

    fun showJokeInfo(joke: Joke)
    fun showErrorAndCloseScreen(errorMessage: String)
}