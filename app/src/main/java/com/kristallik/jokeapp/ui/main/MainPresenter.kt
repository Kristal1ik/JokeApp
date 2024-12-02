package com.kristallik.jokeapp.ui.main

import android.content.Context
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeListResponse
import com.kristallik.jokeapp.data.Source
import com.kristallik.jokeapp.db.JokeDatabase
import com.kristallik.jokeapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPresenter(private val view: MainView) {
    private var currentPage = 0
    private val jokesPerPage = 10

    suspend fun loadJokes(context: Context) {
        loadSavedJokes(context)
        loadMoreJokes(context)
    }

    private suspend fun loadSavedJokes(context: Context) {
        val jokeDao = JokeDatabase.getDatabase(context).jokeDao()
        val savedJokes = withContext(Dispatchers.IO) {
            jokeDao.getAllJokesSaved()
        }

        if (savedJokes.isNotEmpty()) {
            view.showJokes(savedJokes)
        } else {
            view.showError("No saved jokes available!")
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

            val jokeDao = JokeDatabase.getDatabase(context).jokeDao()

            withContext(Dispatchers.IO) {
                for (joke in response.jokes) {
                    val newNetworkJoke = Joke(
                        category = joke.category,
                        setup = joke.setup,
                        delivery = joke.delivery,
                        source = Source.TYPE_NETWORK
                    )
                    jokeDao.insertJoke(newNetworkJoke)
                }
            }

            if (response.jokes.isEmpty()) {
                view.showError("No more jokes available!")
            } else {
                currentPage++
                loadSavedJokes(context)
            }
        } catch (e: Exception) {
            println(e)
            view.showError("Failed to load jokes!")
        }
    }

    fun onActionButtonClicked() {
        view.addJoke()
    }
}