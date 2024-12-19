package com.kristallik.jokeapp.data.repository

import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.data.source.local.JokeDatabase
import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.presentation.mapper.JokeMapper

interface JokeRepository {
    suspend fun getAllSavedJokes(): List<Joke>
    suspend fun insertSavedJoke(joke: SavedJoke)
    suspend fun getAllNetworkJokes(): List<NetworkJoke>
    suspend fun insertNetworkJoke(joke: NetworkJoke)
}

class JokeRepositoryImpl(
    private val database: JokeDatabase,
    private val jokeMapper: JokeMapper
) : JokeRepository {
    override suspend fun getAllSavedJokes(): List<Joke> {
        return database.savedJokeDao().getAllJokesSaved().map {
            jokeMapper.mapSavedToJoke(it)
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