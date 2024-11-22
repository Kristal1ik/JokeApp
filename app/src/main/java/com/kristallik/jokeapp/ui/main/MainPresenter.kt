package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.JokeGenerator.jokes

class MainPresenter(private val view: MainView) {
    suspend fun loadJokes() {
        if (jokes.isEmpty()) {
            view.showError("There are no jokes available!")
        } else {
            view.showJokes(jokes)
        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}