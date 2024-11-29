package com.kristallik.jokeapp.ui.main

import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.data.JokeListResponse
import com.kristallik.jokeapp.data.Source
import com.kristallik.jokeapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPresenter(private val view: MainView) {
    private val generator = JokeGenerator
    private var currentPage = 0
    private val jokesPerPage = 10


    suspend fun loadJokes() {
        if (!generator.isLocalLoaded) {
            generator.jokes = generator.generateJokesData()
            generator.isLocalLoaded = true
            currentPage = 0
            println(generator.jokes)
            loadMoreJokes()
        }
    }

    suspend fun loadMoreJokes() {
        try {
            val response: JokeListResponse = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getJokes(
                    amount = jokesPerPage,
                    page = currentPage
                )
            }
            for (joke in response.jokes) {
                generator.jokes.add(
                    Joke(
                        joke.id,
                        joke.category,
                        joke.setup,
                        joke.delivery,
                        Source.TYPE_NETWORK
                    )
                )
            }
            if (response.jokes.isEmpty()) {
                view.showError("No more jokes available!")
            } else {
                currentPage++
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