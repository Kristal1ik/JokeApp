package com.kristallik.jokeapp.ui.main

import android.content.Context
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
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
                val savedJokesDao = JokeDatabase.getDatabase(context).savedJokeDao()
                for (joke in savedJokesDao.getAllJokesSaved()) {
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
//            println(generator.jokes)
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
                val networkJoke = NetworkJoke(
                    joke.id, joke.category,
                    joke.setup,
                    joke.delivery,
                    Source.TYPE_NETWORK,
                    System.currentTimeMillis()
                )
                withContext(Dispatchers.IO) {
                    val networkJokeDao = JokeDatabase.getDatabase(context).networkJokeDao()
                    val existingJoke = networkJokeDao.getJokeById(joke.id)
                    if (existingJoke == null) {
                        networkJokeDao.insertJoke(networkJoke)
                    }
                }
            }
            if (response.jokes.isEmpty()) {
                view.showError("No more jokes available!")
            } else {
                currentPage++
                view.showJokes(generator.jokes)
            }
        } catch (e: Exception) {
            loadCashedJokes(context)
        }
    }

    private suspend fun loadCashedJokes(context: Context) {
        val currentTime = System.currentTimeMillis()
        val validTimeDuration = 24 * 60 * 60 * 1000
        withContext(Dispatchers.Main) {
            val networkJokesDao = JokeDatabase.getDatabase(context).networkJokeDao()
            val networkJokes = networkJokesDao.getAllJokesSaved()
            if (networkJokes.isNotEmpty()) {
                if (currentTime - networkJokes[0].lastUpdated <= validTimeDuration) {
                    for (joke in networkJokesDao.getAllJokesSaved()) {
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
                    view.showToast("Loaded cached jokes due to network failure.")
                    currentPage++
                    view.showJokes(generator.jokes)

                }
            } else {
                view.showToast("No saved jokes available! Please check your internet connection and try again.")
            }
        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}