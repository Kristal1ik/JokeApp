package com.kristallik.jokeapp.domain.usecase

import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.data.repository.JokeRepository

class AddJokeUseCase(private val jokeRepository: JokeRepository) {
    suspend fun execute(joke: SavedJoke) {
        if (joke.setup.isEmpty() || joke.delivery.isEmpty()) {
            throw IllegalArgumentException("Setup and delivery cannot be empty")
        }
        jokeRepository.insertSavedJoke(joke)
    }
}