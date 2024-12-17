package com.kristallik.jokeapp.domain.usecase

import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.repository.JokeRepository

class GetNetworkJokesUseCase(private val jokeRepository: JokeRepository) {
    suspend fun execute(): List<NetworkJoke> {
        return try {
            jokeRepository.getAllNetworkJokes()
        } catch (e: Exception) {
            throw e
        }
    }
}
