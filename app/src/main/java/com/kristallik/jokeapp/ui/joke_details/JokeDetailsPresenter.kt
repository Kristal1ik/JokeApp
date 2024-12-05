package com.kristallik.jokeapp.ui.joke_details

import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsView.Companion.VALUE_IF_ERROR

// Должен знать о view
class JokeDetailsPresenter(private val view: JokeDetailsView) {
    fun loadJokeDetails(position: Int) {
        if (position == VALUE_IF_ERROR) {
            view.showErrorAndCloseScreen("Invalid joke position!")
        } else {
            (JokeGenerator.jokes[position] as? Joke)?.let {
                view.showJokeInfo(it)
            } ?: view.showErrorAndCloseScreen("Invalid joke data!")

        }
    }
}