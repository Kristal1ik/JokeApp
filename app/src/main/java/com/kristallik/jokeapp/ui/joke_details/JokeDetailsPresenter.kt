package com.kristallik.jokeapp.ui.joke_details

import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator

// Должен знать о view
class JokeDetailsPresenter(private val view: JokeDetailsView) {
    fun loadJokeDetails(position: Int){
        if (position == -1){
            view.showErrorAndCloseScreen("Invalid joke position!")
        }
        else{
            (JokeGenerator.jokes[position] as? Joke)?.let {
                view.showJokeInfo(it)
            } ?: view.showErrorAndCloseScreen("Invalid joke data!")

        }    }
}