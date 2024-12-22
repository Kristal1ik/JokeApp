package com.kristallik.jokeapp.data.repository

import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.data.source.local.JokeDatabase
import com.kristallik.jokeapp.data.source.remote.JokeApiService
import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.domain.model.Source
import javax.inject.Inject

interface JokeRepository {
    suspend fun getAllSavedJokes(): List<Joke>
    suspend fun insertSavedJoke(joke: SavedJoke)
    suspend fun getAllNetworkJokes(): List<NetworkJoke>
    suspend fun insertNetworkJoke(joke: NetworkJoke)
}

class JokeRepositoryImpl @Inject constructor(
    private val database: JokeDatabase,
    private val jokeApiService: JokeApiService
) : JokeRepository {
    override suspend fun getAllSavedJokes(): List<Joke> {
        return database.savedJokeDao().getAllJokesSaved().map {
            Joke(it.id, it.category, it.setup, it.delivery, Source.TYPE_MANUAL)
        }
    }

    override suspend fun insertSavedJoke(joke: SavedJoke) {
        database.savedJokeDao().insertJoke(joke)
    }

    override suspend fun getAllNetworkJokes(): List<NetworkJoke> {
        return jokeApiService.getJokes().jokes.map { joke ->
            NetworkJoke(joke.id, joke.category, joke.setup, joke.delivery)
        }
    }


    override suspend fun insertNetworkJoke(joke: NetworkJoke) {
        database.networkJokeDao().insertJoke(joke)
    }
}