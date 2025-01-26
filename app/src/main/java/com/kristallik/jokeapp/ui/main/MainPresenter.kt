package com.kristallik.jokeapp.ui.main

import android.content.Context
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.data.JokeGenerator.isNetworkLoaded
import com.kristallik.jokeapp.data.JokeListResponse
import com.kristallik.jokeapp.data.NetworkJoke
import com.kristallik.jokeapp.data.Source
import com.kristallik.jokeapp.db.JokeDatabase
import com.kristallik.jokeapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPresenter(private val view: MainView) {
    private val generator = JokeGenerator
    private var currentPage = 0
    private val jokesPerPage = 10


    suspend fun loadJokes(context: Context) {
        if (!generator.isLocalLoaded) {
            generator.generateJokesData()
            withContext(Dispatchers.IO) {
                val savedJokes = JokeDatabase.getDatabase(context).savedJokeDao().getAllJokesSaved()
                for (joke in savedJokes) {
                    generator.jokes.add(
                        Joke(
                            joke.id,
                            joke.category,
                            joke.setup,
                            joke.delivery,
                            joke.source
                        )
                    )
                }
            }
            currentPage = 0
            loadMoreJokes(context)
        }
    }

    suspend fun loadMoreJokes(context: Context) {
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
                JokeDatabase.getDatabase(context).networkJokeDao().insertJoke(
                    NetworkJoke(
                        joke.id,
                        joke.category,
                        joke.setup,
                        joke.delivery,
                        Source.TYPE_NETWORK,
                        System.currentTimeMillis()
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
            if (!isNetworkLoaded) {
                isNetworkLoaded = true
                loadCashedJokes(context)
                view.showJokes(generator.jokes)
                view.showError("Failed to load network jokes!")
                view.showError("Loaded cached jokes due to network failure.")
            }
        }
    }

    private suspend fun loadCashedJokes(context: Context) {
        val currentTime = System.currentTimeMillis()
        val validTimeDuration = 24 * 60 * 60 * 1000
        withContext(Dispatchers.IO) {
            val networkJokes =
                JokeDatabase.getDatabase(context).networkJokeDao().getAllJokesNetwork()
            if (networkJokes.isNotEmpty()) {
                if (currentTime - networkJokes[0].lastUpdate <= validTimeDuration) {
                    for (joke in networkJokes) {
                        generator.jokes.add(
                            Joke(
                                joke.id,
                                joke.category,
                                joke.setup,
                                joke.delivery,
                                joke.source
                            )
                        )

                    }
                }
            } else {
                view.showError("No saved jokes available! Please check your internet connection and try again.")
            }
        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}