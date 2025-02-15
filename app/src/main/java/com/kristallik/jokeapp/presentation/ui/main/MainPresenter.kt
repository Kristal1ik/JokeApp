package com.kristallik.jokeapp.presentation.ui.main

import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.data.generator.JokeGenerator
import com.kristallik.jokeapp.data.generator.JokeGenerator.isCachedLoaded
import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.domain.model.Source
import com.kristallik.jokeapp.data.source.remote.RetrofitInstance
import com.kristallik.jokeapp.domain.usecase.GetJokesUseCase
import com.kristallik.jokeapp.domain.usecase.AddNetworkJokeUseCase
import com.kristallik.jokeapp.domain.usecase.GetNetworkJokesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPresenter(
    private val view: MainView,
    private val getJokesUseCase: GetJokesUseCase,
    private val getNetworkJokesUseCase: GetNetworkJokesUseCase,
    private val addNetworkJokeUseCase: AddNetworkJokeUseCase
) {
    private val generator = JokeGenerator
    private var currentPage = 0
    private val jokesPerPage = 10


    suspend fun loadJokes() {
        if (!generator.isLocalLoaded) {
            generator.generateJokesData()
            val savedJokes = withContext(Dispatchers.IO) {
                getJokesUseCase.execute()
            }
            generator.jokes.addAll(savedJokes)
            currentPage = 0
            loadMoreJokes()
        } else {
            view.showJokes(generator.jokes)
        }
    }

    suspend fun loadMoreJokes() {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getJokes(amount = jokesPerPage, page = currentPage)
            }

            for (joke in response.jokes) {
                val networkJoke = NetworkJoke(
                    joke.id,
                    joke.category,
                    joke.setup,
                    joke.delivery,
                    System.currentTimeMillis()
                )
                generator.jokes.add(
                    Joke(
                        networkJoke.id,
                        networkJoke.category,
                        networkJoke.setup,
                        networkJoke.delivery,
                        Source.TYPE_NETWORK
                    )
                )
                addNetworkJokeUseCase.execute(networkJoke)
            }

            if (response.jokes.isEmpty()) {
                view.showError("No more jokes available!")
            } else {
                currentPage++
                view.showJokes(generator.jokes)
            }
        } catch (e: Exception) {
            loadCachedJokes()
        }
    }


    private suspend fun loadCachedJokes() {
        if (!isCachedLoaded) {
            val currentTime = System.currentTimeMillis()
            val validTimeDuration = 24 * 60 * 60 * 1000

            withContext(Dispatchers.IO) {
                val cachedNetworkJokes = getNetworkJokesUseCase.execute()

                if (cachedNetworkJokes.isNotEmpty()) {
                    if (currentTime - cachedNetworkJokes[0].lastUpdated <= validTimeDuration) {
                        generator.jokes.addAll(cachedNetworkJokes.map {
                            Joke(it.id, it.category, it.setup, it.delivery, Source.TYPE_NETWORK)
                        })
                        withContext(Dispatchers.Main) {
                            view.showToast("Loaded cached jokes due to network failure.")
                            view.showJokes(generator.jokes)
                        }
                        currentPage++
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view.showToast("No saved jokes available! Please check your internet connection and try again.")
                    }
                }
            }
            isCachedLoaded = true

        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}