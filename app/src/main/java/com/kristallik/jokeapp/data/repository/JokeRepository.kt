package com.kristallik.jokeapp.data.repository

import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.data.source.local.JokeDatabase
import com.kristallik.jokeapp.domain.model.Joke

interface JokeRepository {
    suspend fun getAllSavedJokes(): List<Joke>
    suspend fun insertSavedJoke(joke: SavedJoke)
    suspend fun getAllNetworkJokes(): List<NetworkJoke>
    suspend fun insertNetworkJoke(joke: NetworkJoke)
}

class JokeRepositoryImpl(private val database: JokeDatabase) : JokeRepository {
    override suspend fun getAllSavedJokes(): List<Joke> {
        return database.savedJokeDao().getAllJokesSaved().map {
            Joke(it.id, it.category, it.setup, it.delivery, it.source)
        }
    }

    override suspend fun insertSavedJoke(joke: SavedJoke) {
        database.savedJokeDao().insertJoke(joke)
    }

    override suspend fun getAllNetworkJokes(): List<NetworkJoke> {
        return database.networkJokeDao().getAllJokesSaved()
    }

    override suspend fun insertNetworkJoke(joke: NetworkJoke) {
        database.networkJokeDao().insertJoke(joke)
    }
}