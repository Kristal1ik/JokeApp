package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.data.JokeListResponse
import com.kristallik.jokeapp.network.RetrofitInstance
import com.kristallik.jokeapp.ui.main.fragment.count

class MainPresenter(private val view: MainView) {
    private val generator = JokeGenerator
    private var currentPage = 0
    private val jokesPerPage = 10

    suspend fun loadJokes() {
        if (count == 0) {
            generator.jokes = generator.generateJokesData()
            currentPage = 0
        }

        loadMoreJokes()
    }

    suspend fun loadMoreJokes() {
        try {
            val response: JokeListResponse =
                RetrofitInstance.api.getJokes(amount = jokesPerPage, page = currentPage)
            for (joke in response.jokes) {
                generator.jokes.add(Joke(joke.id, joke.category, joke.setup, joke.delivery, "net"))
            }
            if (response.jokes.isEmpty()) {
                view.showError("No more jokes available!")
            } else {
                currentPage++
                count += response.jokes.size
                view.showJokes(generator.jokes)
            }
        } catch (e: Exception) {
            view.showError("Failed to load jokes!")
        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}