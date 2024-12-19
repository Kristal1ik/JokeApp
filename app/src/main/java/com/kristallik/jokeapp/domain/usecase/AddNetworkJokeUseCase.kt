package com.kristallik.jokeapp.domain.usecase

import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.repository.JokeRepository

class AddNetworkJokeUseCase(private val jokeRepository: JokeRepository) {
    suspend fun execute(joke: NetworkJoke) {
        jokeRepository.insertNetworkJoke(joke)
    }
}
