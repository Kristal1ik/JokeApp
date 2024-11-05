package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.JokeGenerator

class MainPresenter(private val view: MainView) {
    private val generator = JokeGenerator
    fun loadJokes() {
        val data = generator.generateJokesData()
        if (data.isEmpty()) {
            view.showError("There are no jokes available!")
        } else {
            view.showJokes(data)
        }
    }
}